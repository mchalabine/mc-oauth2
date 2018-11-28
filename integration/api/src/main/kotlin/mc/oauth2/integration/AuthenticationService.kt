package mc.oauth2.integration

import mc.oauth2.Credentials
import mc.oauth2.Principal

/**
 * @author Michael Chalabine
 */
interface AuthenticationService {

    fun authenticate(principal: Principal, credentials: Credentials): AuthenticationResult

}
