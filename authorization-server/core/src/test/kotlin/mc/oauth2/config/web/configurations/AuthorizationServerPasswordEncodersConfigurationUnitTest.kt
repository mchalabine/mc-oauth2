package mc.oauth2.config.web.configurations

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest(classes = [(AuthorizationServerPasswordEncodersConfiguration::class)])
internal class AuthorizationServerPasswordEncodersConfigurationUnitTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun `test can retrieve password encoder bean`() {
        val passwordEncoder = context.getBean(PasswordEncoder::class.java)
        assertNotNull(passwordEncoder)
    }

    @Test
    fun `test pasword encoder is of expected type`() {
        val passwordEncoder = context.getBean(PasswordEncoder::class.java)
        assertTrue(BCryptPasswordEncoder::class.isInstance(passwordEncoder))
    }
}

