package mc.oauth2.config.web.configurations

import mc.oauth2.Profiles
import mc.oauth2.User
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_USERNAME
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.support.integration.InMemoryAuthenticationService
import mc.oauth2.support.integration.InMemoryAuthenticationService.InMemoryAuthenticationServiceBuilderUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * @author Michael Chalabine
 */
@Configuration
@Profile(Profiles.IN_MEM)
class AuthenticationServiceConfiguration {

    @Bean
    fun authenticationService(): AuthenticationService {
        val builder = getInMemoryAuthenticationServiceBuilder()
        return build(builder)
    }

    private fun build(builder: InMemoryAuthenticationServiceBuilderUser): AuthenticationService {
        return builder
                .withUser(User.aUser()
                        .withPrincipal(TEST_USERNAME)
                        .withCredentials(TEST_PASSWORD)
                        .build())
                .build()
    }

    private fun getInMemoryAuthenticationServiceBuilder(): InMemoryAuthenticationServiceBuilderUser {
        return InMemoryAuthenticationService.aService()
    }

}
