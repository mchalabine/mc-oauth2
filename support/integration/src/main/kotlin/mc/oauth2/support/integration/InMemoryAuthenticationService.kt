package mc.oauth2.support.integration

import mc.oauth2.config.AuthenticationResult
import mc.oauth2.config.AuthenticationResult.AUTHENTICATED
import mc.oauth2.config.AuthenticationService
import mc.oauth2.config.Credentials
import mc.oauth2.config.Principal

/**
 * @author Michael Chalabine
 */
class InMemoryAuthenticationService : AuthenticationService {

    override fun authenticate(principal: Principal,
                              credentials: Credentials): AuthenticationResult {
        return AUTHENTICATED
    }

}
