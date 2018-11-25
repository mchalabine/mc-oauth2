package mc.oauth2.integration

import mc.oauth2.config.Credentials
import mc.oauth2.config.Principal

/**
 * @author Michael Chalabine
 */
interface AuthenticationService {

    fun authenticate(principal: Principal, credentials: Credentials): AuthenticationResult

}
