package mc.oauth2

/**
 * @author Michael Chalabine
 */
interface AuthenticationService {

    fun authenticate(principal: Principal, credentials: Credentials): AuthenticationResult

}
