package mc.oauth2

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import javax.servlet.Filter


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(McOAuth2AuthorizationServerSecurityConfigurer::class)])
@WebAppConfiguration
class McOAuth2AuthorizationServerSecurityConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var springSecurityFilterChain: Filter

    lateinit var mockMvc: MockMvc

    @Before
    fun before() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter<DefaultMockMvcBuilder>(springSecurityFilterChain)
                .build()
    }

    @Test
    fun testAuthenticationManagerAuthenticatesAsExpectedWhereUserMatches() {
        val token = getValidAuthenticationToken()
        val authenticate = authenticationManager.authenticate(token)
        assertTrue(authenticate.isAuthenticated)
    }

    @Test(expected = BadCredentialsException::class)
    fun testAuthenticationManagerRejectsAsExpectedWhereUserMatchesNot() {
        val token = UsernamePasswordAuthenticationToken("user1", "password")
        val authenticate = authenticationManager.authenticate(token)
        assertFalse(authenticate.isAuthenticated)
    }

    @Test
    fun testHttpSecurityAllowsGetLogin() {
        mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun testHttpSecurityAllowsGetAuthorize() {
        mockMvc.perform(get("/authorize"))
                .andExpect(status().is3xxRedirection)
    }

    @Test
    fun testAuthenticationManagerAuthenticatesAsExpectedRoleWhereUserMatches() {
        val token = getValidAuthenticationToken()
        val authenticate = authenticationManager.authenticate(token)
        val actual = authenticate.authorities
        val expected = SimpleGrantedAuthority("ROLE_${McOAuth2AuthorizationServerConstants.ROLES_USER}")
        assertTrue(actual.contains(expected))
    }

    @Test
    fun testHttpSecurityAuthenticatesAtLoginWhereUserMatches() {
        val token = getValidAuthenticationToken()
        mockMvc.perform(post("/login")
                .with(authentication(token)))
                .andExpect(authenticated())
    }

    @Test
    @Ignore
    fun testHttpSecurityAllowsLoginWithSupportedRolesWhereAuthenticated() {
        val token = getValidAuthenticationToken()
        mockMvc.perform(post("/login")
                .with(authentication(token)))
                .andExpect(authenticated()
                        .withRoles(McOAuth2AuthorizationServerConstants.ROLES_USER))
    }

    @Test
    fun testHttpSecurityAllowsAuthorize() {
        val authenticationToken = getValidAuthenticationToken()
        mockMvc.perform(post("/authorize")
                .with(authentication(authenticationToken)))
                .andExpect(authenticated())
    }

    private fun getValidAuthenticationToken() = UsernamePasswordAuthenticationToken("user", "password")
}
