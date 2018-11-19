package mc.oauth2.support.test

import mc.oauth2.config.AuthenticationService
import mc.oauth2.support.integration.InMemoryTestAuthenticationService
import org.springframework.boot.test.context.TestConfiguration

/**
 * @author Michael Chalabine
 */
@TestConfiguration
class AuthorizationServerTestSecurityConfigurer {

    fun authenticationService() : AuthenticationService {
        val service = InMemoryTestAuthenticationService()
        TODO("Not yet implemented")
    }
}