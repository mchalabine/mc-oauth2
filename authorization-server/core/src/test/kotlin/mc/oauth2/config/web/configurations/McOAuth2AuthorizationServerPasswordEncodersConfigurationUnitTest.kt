package mc.oauth2.config.web.configurations

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [(McOAuth2AuthorizationServerPasswordEncodersConfiguration::class)])
class McOAuth2AuthorizationServerPasswordEncodersConfigurationUnitTest {

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

