package mc.oauth2.app

import io.mockk.mockk
import mc.oauth2.Profiles
import mc.oauth2.integration.DelegatingClientDetailsService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles(Profiles.TEST, Profiles.IN_MEM)
@SpringBootTest(classes = [Application::class, ApplicationTestConfiguration::class])
class ApplicationUnitTest {

    @Test
    fun testRunning() {
    }
}

@TestConfiguration
@Profile(Profiles.TEST)
internal class ApplicationTestConfiguration {

    @Bean
    fun userAuthenticationProvider(): AuthenticationProvider {
        return mockk()
    }

    @Bean
    fun delegatingClientDetailsService() : ClientDetailsService {
        return mockk()
    }

}
