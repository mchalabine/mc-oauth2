package mc.oauth2

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import javax.servlet.Filter
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*


/**
 */
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
                .build();
    }

    @Test
    fun testAuthenticationManagerAuthenticatesAsExpectedWhereUserMatches() {
        val authenticationToken = UsernamePasswordAuthenticationToken("user", "password")
        val authenticate = authenticationManager.authenticate(authenticationToken)
        assertTrue(authenticate.isAuthenticated)
    }

    @Test
    fun testHttpSecurityAllowsLogin() {
        val authenticationToken = UsernamePasswordAuthenticationToken("user", "password")
        mockMvc.perform(post("/login")
                .with(authentication(authenticationToken)))
                .andExpect(authenticated())
    }

    @Test
    fun testHttpSecurityAllowsAuthorize() {
        val authenticationToken = UsernamePasswordAuthenticationToken("user", "password")
        mockMvc.perform(post("/authorize")
                .with(authentication(authenticationToken)))
                .andExpect(authenticated())
    }
}
