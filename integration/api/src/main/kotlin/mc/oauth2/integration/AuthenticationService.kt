package mc.oauth2.integration

/**
 * @author Michael Chalabine
 */
interface AuthenticationService {

    fun authenticate(principal: Principal, credentials: Credentials): AuthenticationResult

}
