package mc.oauth2.config

/**
 * @author Michael Chalabine
 */
interface AuthenticationService {

    fun authenticate(principal: Principal, credentials: Credentials): AuthenticationResult

}
