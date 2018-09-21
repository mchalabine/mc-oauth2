package mc.oauth2

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Mikhail Chalabine
 * @since
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(McAuth2AuthorizationServerPasswordEncodersConfigurer::class))
class McAuth2AuthorizationServerPasswordEncodersConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun testCanRetrievePasswordEncoderBean() {
        val passwordEncoder = context.getBean(PasswordEncoder::class.java)
        assertNotNull(passwordEncoder)
    }

    @Test
    fun testPasswordEncoderBeanHasTypeExpected() {
        val passwordEncoder = context.getBean(PasswordEncoder::class.java)
        assertTrue(BCryptPasswordEncoder::class.isInstance(passwordEncoder))
    }
}

