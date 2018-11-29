package mc.oauth2.config.web.configurations

import mc.oauth2.ROLE_ADMIN
import mc.oauth2.ROLE_USER
import mc.oauth2.URI_LOGIN
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_USERNAME
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
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

@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [McOAuth2AuthorizationServerSecurityConfiguration::class,
    McOAuth2AuthenticationServiceConfiguration::class])
internal class McOAuth2AuthorizationServerSecurityConfigurationUnitTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var springSecurityFilterChain: Filter

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply { SecurityMockMvcConfigurers.springSecurity() }
                .addFilter<DefaultMockMvcBuilder>(springSecurityFilterChain)
                .build()
    }

    @Test
    fun testAuthenticationManagerAuthenticatesAsExpectedWhereUserMatches() {
        val token = getValidAuthenticationToken()
        val actual = authenticationManager.authenticate(token)
        assertTrue(actual.isAuthenticated)
    }

    @Test
    fun testAuthenticationManagerRejectsAsExpectedWhereUserMatchesNot() {
        val token = getInvalidAuthenticationToken()
        assertThrows(AuthenticationServiceException::class.java) {
            authenticationManager.authenticate(token)
        }
    }

    private fun getInvalidAuthenticationToken(): UsernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken("user1", "password")

    @Test
    fun testHttpSecurityAllowsGetLogin() {
        mockMvc.perform(get(URI_LOGIN))
                .andDo(print())
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun testHttpSecurityAllowsGetAuthorize() {
        mockMvc.perform(get("/oauth/authorize"))
                .andExpect(status().is3xxRedirection)
    }

    @Test
    fun testAuthenticationManagerAuthenticatesAsExpectedRoleWhereUserMatches() {
        val token = getValidAuthenticationToken()
        val actual = authenticationManager.authenticate(token)
        val expected = getExpectedAuthority()
        assertTrue(actual.authorities.contains(expected))
    }

    private fun getExpectedAuthority(): SimpleGrantedAuthority {
        val expectedRoleName = "ROLE_$ROLE_USER"
        return SimpleGrantedAuthority(expectedRoleName)
    }

    @Test
    fun testHttpSecurityAuthenticatesAtPostAtLoginWhereUserMatches() {
        val token: Authentication = getValidAuthenticationToken()
        login(token).andExpect(authenticated())
    }

    @Test
    fun testHttpSecurityReturnsExpectedStatusAtPostAtLoginWhereUserMatches() {
        val token: Authentication = getValidAuthenticationToken()
        login(token).andExpect(status().is3xxRedirection)
    }

    @Test
    fun testCsrfRejectsRequestWhereCsrfTokenPresentNot() {
        mockMvc.perform(post(URI_LOGIN)).andExpect(status().isForbidden)
    }

    @Test
    fun testCsrfAllowsRequestToAuthorizationPathWhereCsrfTokenPresentNot() {
        val builder = UriComponentsBuilder.fromPath("/")
        val authorize = MvcUriComponentsBuilder
                .on(AuthorizationEndpoint::class.java)
                .authorize(null, null, null, null)
        val path: String = MvcUriComponentsBuilder.fromMethodCall(builder, authorize).build().path!!

        mockMvc.perform(post(path)).andExpect(status().is3xxRedirection)
    }

    @Test
    fun testCsrfAllowsRequestWhereCsrfTokenPresent() {
        mockMvc.perform(post(URI_LOGIN).with(csrf())).andExpect(status().is3xxRedirection)
    }

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

    @Test
    fun testHttpSecurityReportsNoAuthoritiesAtPostAtLoginWhereInvalidToken() {
        val token = getInvalidAuthenticationToken()
        val expected = authenticated().withAuthentication(token)
        mockMvc.perform(post(URI_LOGIN)
                .with(authentication(token)))
                .andExpect(expected)
                .andDo(print())
    }

    @Test
    fun testHttpSecurityAuthenticatesNotAtPostAtLoginWhereUserMatchesNot() {
        val token = getInvalidAuthenticationToken()
        login(token).andExpect(unauthenticated())
    }

    @Test
    fun testHttpSecurityAuthenticatesWithRoleUserAtPostAtLoginWhereUserMatches() {
        val token = getValidAuthenticationToken()
        login(token).andExpect(authenticated().withRoles(ROLE_USER))
    }

    @Test
    fun testHttpSecurityAllowsAuthorize() {
        val authenticationToken = getValidAuthenticationToken()
        mockMvc.perform(post("/authorize")
                .with(authentication(authenticationToken)))
                .andExpect(authenticated())
    }

    private fun getValidAuthenticationToken(): Authentication {
        return UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD)
    }

}
