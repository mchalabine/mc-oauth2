package mc.oauth2.configurations

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerPasswordEncodersConfiguration::class])
internal class AuthorizationServerPasswordEncodersConfigurationUnitTest(
        val applicationContext: ApplicationContext) {

    @Test
    fun `test can retrieve password encoder bean`() {
        val passwordEncoder = applicationContext.getBean(PasswordEncoder::class.java)
        assertNotNull(passwordEncoder)
    }

    @Test
    fun `test password encoder is of expected type`() {
        val passwordEncoder = applicationContext.getBean(PasswordEncoder::class.java)
        assertTrue(BCryptPasswordEncoder::class.isInstance(passwordEncoder))
    }
}

