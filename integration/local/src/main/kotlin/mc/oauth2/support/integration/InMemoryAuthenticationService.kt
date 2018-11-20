package mc.oauth2.support.integration

import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.integration.Credentials
import mc.oauth2.integration.Principal

/**
 * @author Michael Chalabine
 */
class InMemoryAuthenticationService : AuthenticationService {

    override fun authenticate(principal: Principal,
                              credentials: Credentials): AuthenticationResult {
        return AUTHENTICATED
    }

}
