package mc.oauth2.config

import mc.oauth2.integration.AuthenticationService
import mc.oauth2.support.integration.InMemoryAuthenticationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Michael Chalabine
 */
@Configuration
class McOAuth2AuthenticationServiceConfigurer {

    @Bean
    fun authenticationService(): AuthenticationService {
        return InMemoryAuthenticationService()
    }

}
