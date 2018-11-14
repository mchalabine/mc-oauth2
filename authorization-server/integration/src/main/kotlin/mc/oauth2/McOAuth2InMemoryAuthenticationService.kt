package mc.oauth2

import mc.oauth2.AuthenticationResult.AUTHENTICATED

/**
 * @author Michael Chalabine
 */
class McOAuth2InMemoryAuthenticationService : AuthenticationService {

    override fun authenticate(principal: Principal,
                              credentials: Credentials): AuthenticationResult {
        return AUTHENTICATED
    }

}
