package mc.oauth2.config.web.configurations

import mc.oauth2.User
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.support.integration.InMemoryAuthenticationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Michael Chalabine
 */
@Configuration
class McOAuth2AuthenticationServiceConfiguration {

    @Bean
    fun authenticationService(): AuthenticationService {
        return InMemoryAuthenticationService.aService()
                .withUser(User.aUser()
                        .withPrincipal("my-principal")
                        .withCredentials("my-credentials")
                        .build())
                .build()
    }

}
