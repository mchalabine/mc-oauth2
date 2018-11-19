package mc.oauth2.config

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

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(McOAuth2AuthorizationServerPasswordEncodersConfigurer::class)])
class McOAuth2AuthorizationServerPasswordEncodersConfigurerUnitTest {

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

