package mc.oauth2

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Mikhail Chalabine
 * @since
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(McOAuth2AuthorizationServerSecurityConfigurer::class))
class McOAuth2AuthorizationServerSecurityConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun teatAuthenticationManagerAuthenticatesAsExpectedWhereUserMatches() {
        val authenticationManager = context.getBean(AuthenticationManager::class.java)
        val authenticationToken = UsernamePasswordAuthenticationToken("user", "password")
        val authenticate = authenticationManager.authenticate(authenticationToken)
        assertTrue(authenticate.isAuthenticated())
    }
}
