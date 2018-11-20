package mc.oauth2.integration

/**
 * @author Michael Chalabine
 */
interface AuthenticationService {

    fun authenticate(principal: Principal, credentials: Credentials): AuthenticationResult

}


//domain-test -> should contain testdata
//integration -> api, local, support, test
//support -> remove
//integration-test -> remove
//integration-api -> remove
