package mc.oauth2.config.web.configurations

import mc.oauth2.Profiles
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.providers.DelegatingAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
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
