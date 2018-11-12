package mc.oauth2

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(McOAuth2AuthorizationServerConfigurer::class))
@Ignore
class McOAuth2AuthorizationServerConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun testRunning() {
    }

    @Test
    fun testApplicationContextInjected() {
        assertNotNull(context)
    }

    @Test
    fun testListBeanNames() {
        context.beanDefinitionNames.forEach { println(it) }
    }

    @Test
    fun teatAuthenticationManagerReturnsExpectedWhereCreated() {
        val authenticationManager= context.getBean(AuthenticationManager::class.java)
//        val authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken("user", "password"))
//        assertTrue(authenticate.isAuthenticated())
    }
}