package mc.oauth2.support.test

import mc.oauth2.config.AuthenticationService
import mc.oauth2.support.integration.InMemoryAuthenticationService
import org.springframework.boot.test.context.TestConfiguration

/**
 * @author Michael Chalabine
 */
@TestConfiguration
class AuthorizationServerTestSecurityConfigurer {

    fun authenticationService() : AuthenticationService {
        val service = InMemoryAuthenticationService()
        TODO("Not yet implemented")
    }
}