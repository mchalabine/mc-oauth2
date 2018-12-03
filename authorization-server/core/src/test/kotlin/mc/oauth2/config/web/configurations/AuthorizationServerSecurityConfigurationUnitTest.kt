package mc.oauth2.config.web.configurations

import io.mockk.every
import io.mockk.mockk
import mc.oauth2.MSG_AUTHENTICATION_FAILURE
import mc.oauth2.Profiles
import mc.oauth2.Roles
import mc.oauth2.URI_LOGIN
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_ROLES
import mc.oauth2.config.TEST_USERNAME
import mc.oauth2.config.web.configurations.AuthorizationServerSecurityConfigurationUnitTest.AuthorizationServerSecurityTestConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.Filter

@ActiveProfiles(*[Profiles.TEST, Profiles.IN_MEM])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerSecurityConfiguration::class,
    AuthorizationServerSecurityTestConfiguration::class])
internal class AuthorizationServerSecurityConfigurationUnitTest {

    @Autowired
    lateinit var springSecurityFilterChain: Filter

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext


    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply { SecurityMockMvcConfigurers.springSecurity() }
                .addFilter<DefaultMockMvcBuilder>(springSecurityFilterChain)
                .build()
    }

    @Test
    fun `test can GET Login`() {
        mockMvc.perform(get(URI_LOGIN))
                .andDo(print())
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun `test can GET Authorize`() {
        mockMvc.perform(get("/oauth/authorize"))
                .andExpect(status().is3xxRedirection)
    }

    @Test
    fun `test authenticates where user matches`() {
        val token: Authentication = getValidAuthenticationToken()
        login(token).andExpect(authenticated())
    }

    @Test
    fun `test returns expected status @POST @LOGIN where user matches`() {
        val token: Authentication = getValidAuthenticationToken()
        login(token).andExpect(status().is3xxRedirection)
    }

    @Test
    fun `test CSRF rejects request @LOGIN where CSRF token missing`() {
        mockMvc.perform(post(URI_LOGIN)).andExpect(status().isForbidden)
    }

    @Test
    fun `test CSRF allows request @LOGIN where CSRF token present`() {
        mockMvc.perform(post(URI_LOGIN).with(csrf())).andExpect(status().is3xxRedirection)
    }

    @Test
    fun `test CSRF allows request to @Authorization where CSRF token missing`() {
        val builder = UriComponentsBuilder.fromPath("/")
        val authorize = MvcUriComponentsBuilder
                .on(AuthorizationEndpoint::class.java)
                .authorize(null, null, null, null)
        val path: String = MvcUriComponentsBuilder.fromMethodCall(builder, authorize).build().path!!

        mockMvc.perform(post(path)).andExpect(status().is3xxRedirection)
    }

    @Test
    fun `test receives no authorities from post @LOGIN where authentication invalid`() {
        val token = getInvalidAuthenticationToken()
        val expected = authenticated().withAuthentication(token)
        mockMvc.perform(post(URI_LOGIN)
                .with(authentication(token)))
                .andExpect(expected)
                .andDo(print())
    }

    @Test
    fun `test rejects post @LOGIN where user invalid`() {
        val token = getInvalidAuthenticationToken()
        login(token).andExpect(unauthenticated())
    }

    @Test
    fun `test authenticates with role user @LOGIN where user matches`() {
        val token = getValidAuthenticationToken()
        login(token).andExpect(authenticated().withRoles(Roles.USER))
    }

    @Test
    fun `test allows to post @AUTHORIZE`() {
        val authenticationToken = getValidAuthenticationToken()
        mockMvc.perform(post("/authorize")
                .with(authentication(authenticationToken)))
                .andExpect(authenticated())
    }

    private fun getValidAuthenticationToken(): Authentication {
        return UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD)
    }

    private fun getInvalidAuthenticationToken(): UsernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken("user1", "password")

    private fun login(token: Authentication): ResultActions {
        val csrf: RequestPostProcessor = csrf()
        return login(token, csrf)
    }

    private fun login(token: Authentication, csrf: RequestPostProcessor): ResultActions {
        return mockMvc.perform(post(URI_LOGIN)
                .param("username", token.principal.toString())
                .param("password", token.credentials.toString())
                .with(csrf))
                .andDo(print())
    }

    @TestConfiguration
    @Profile(Profiles.TEST)
    internal class AuthorizationServerSecurityTestConfiguration {

        @Bean
        fun userAuthenticationProvider(): AuthenticationProvider {
            val provider = mockk<AuthenticationProvider>()
            return stageAuthenticationProvider(provider)
        }

        private fun stageAuthenticationProvider(
                provider: AuthenticationProvider): AuthenticationProvider {
            stageAuthenticate(provider)
            stageSupports(provider)
            return provider
        }

        private fun stageAuthenticate(provider: AuthenticationProvider) {
            stageAuthenticateFailure(provider)
            stageAuthenticateSuccess(provider)
        }

        private fun stageAuthenticateSuccess(provider: AuthenticationProvider) {
            every { provider.authenticate(getValidAuthentication()) }
                    .returns(getValidAuthenticationToken())
        }

        private fun stageAuthenticateFailure(provider: AuthenticationProvider) {
            every { provider.authenticate(any()) }
                    .throws(AuthenticationServiceException(MSG_AUTHENTICATION_FAILURE))
        }

        private fun getValidAuthentication(): Authentication {
            val token = UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD)
            token.details = WebAuthenticationDetails(MockHttpServletRequest())
            return token
        }

        private fun getValidAuthenticationToken(): Authentication =
                UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD, TEST_ROLES)

        private fun stageSupports(provider: AuthenticationProvider) {
            every { provider.supports(any()) }.returns(true)
        }
    }

}
