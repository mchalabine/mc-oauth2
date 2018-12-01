package mc.oauth2.config.web.configurations

import mc.oauth2.integration.AuthenticationService
import mc.oauth2.providers.McOAuth2DelegatingAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.AuthenticationProvider

/**
 * @author Michael Chalabine
 */
@Configuration
@Import(McOAuth2AuthenticationServiceConfiguration::class)
class McOAuth2DelegatingAuthenticationProviderConfiguration(
        private val authenticationService: AuthenticationService) {

    @Bean
    fun userAuthenticationProvider(): AuthenticationProvider {
        return McOAuth2DelegatingAuthenticationProvider(authenticationService)
    }

}
