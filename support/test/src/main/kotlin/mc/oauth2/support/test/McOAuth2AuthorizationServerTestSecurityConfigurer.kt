package mc.oauth2.support.test

import mc.oauth2.config.AuthenticationService
import org.springframework.boot.test.context.TestConfiguration

/**
 * @author Michael Chalabine
 */
@TestConfiguration
class McOAuth2AuthorizationServerTestSecurityConfigurer {

    fun authenticationService() : AuthenticationService {
        val service = McOAuth2InMemoryAuthenticationService()
        TODO("Not yet implemented")
    }
}