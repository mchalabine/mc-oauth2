package mc.oauth2.configurations

import io.mockk.every
import io.mockk.mockk
import mc.oauth2.MSG_AUTHENTICATION_FAILURE
import mc.oauth2.Profiles
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_ROLES
import mc.oauth2.config.TEST_USERNAME
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles(Profiles.TEST, Profiles.IN_MEM)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerConfiguration::class,
    AuthorizationServerTestConfiguration::class])
@ExtendWith(SpringExtension::class)
internal class AuthorizationServerConfigurationUnitTest(
        @Autowired private val applicationContext: WebApplicationContext) {

    @Test
    fun testInstantiate() {
        println(applicationContext)
    }
}

@TestConfiguration
@Profile(Profiles.TEST)
@EnableWebSecurity
class AuthorizationServerTestConfiguration {

    @Bean
    fun userAuthenticationProvider(): AuthenticationProvider {
        val provider = mockk<AuthenticationProvider>()
        return stageAuthenticationProvider(provider)
    }

    @Bean
    fun clientDetailsService(): ClientDetailsService {
        return mockk()
    }

    private fun stageAuthenticationProvider(
            provider: AuthenticationProvider): AuthenticationProvider {
        stageAuthenticate(provider)
        stageSupports(provider)
        return provider
    }

    private fun stageAuthenticate(provider: AuthenticationProvider) {
        stageAuthenticateFailure(provider)
        stageAuthenticateSuccess(provider)
    }

    private fun stageAuthenticateSuccess(provider: AuthenticationProvider) {
        every { provider.authenticate(getValidAuthentication()) }
                .returns(getValidAuthenticationToken())
    }

    private fun stageAuthenticateFailure(provider: AuthenticationProvider) {
        every { provider.authenticate(any()) }
                .throws(AuthenticationServiceException(MSG_AUTHENTICATION_FAILURE))
    }

    private fun getValidAuthentication(): Authentication {
        val token = UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD)
        token.details = WebAuthenticationDetails(MockHttpServletRequest())
        return token
    }

    private fun getValidAuthenticationToken(): Authentication =
            UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD, TEST_ROLES)

    private fun stageSupports(provider: AuthenticationProvider) {
        every { provider.supports(any()) }.returns(true)
    }
}