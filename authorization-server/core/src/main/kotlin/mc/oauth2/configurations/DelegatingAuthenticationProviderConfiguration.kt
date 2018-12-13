package mc.oauth2.configurations

import mc.oauth2.Profiles
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.providers.DelegatingAuthenticationProvider
import org.springframework.context.annotation.*
import org.springframework.security.authentication.AuthenticationProvider

/**
 * @author Michael Chalabine
 */
@Configuration
@Profile(Profiles.LIVE)
@Import(AuthenticationServiceConfiguration::class)
class DelegatingAuthenticationProviderConfiguration(
        private val authenticationService: AuthenticationService) {

    @Bean
    fun userAuthenticationProvider(): AuthenticationProvider {
        return DelegatingAuthenticationProvider(authenticationService)
    }

}
