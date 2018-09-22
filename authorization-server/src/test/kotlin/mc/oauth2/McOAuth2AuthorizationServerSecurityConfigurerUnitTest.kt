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

/**
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(McOAuth2AuthorizationServerSecurityConfigurer::class)])
class McOAuth2AuthorizationServerSecurityConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext

    lateinit var authenticationManager: AuthenticationManager

    @Before
    fun before() {
        authenticationManager = context.getBean(AuthenticationManager::class.java)
    }

    @Test
    fun teatAuthenticationManagerAuthenticatesAsExpectedWhereUserMatches() {
        val authenticationToken = UsernamePasswordAuthenticationToken("user", "password")
        val authenticate = authenticationManager.authenticate(authenticationToken)
        assertTrue(authenticate.isAuthenticated)
    }

    @Test
    fun testHttpSecurityAllowsLogin() {

    }
}
