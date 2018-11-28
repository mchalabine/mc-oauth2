package mc.oauth2.support.integration

import mc.oauth2.User
import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.Credentials
import mc.oauth2.Principal

/**
 * @author Michael Chalabine
 */
class InMemoryAuthenticationService : AuthenticationService {

    private constructor(builder: InMemoryAuthenticationServiceBuilder){
    }

    override fun authenticate(principal: Principal,
                              credentials: Credentials): AuthenticationResult {
        return AUTHENTICATED
    }

    companion object {
        fun aService(): InMemoryAuthenticationServiceBuilderUser {
            return InMemoryAuthenticationServiceBuilder()
        }
    }

    class InMemoryAuthenticationServiceBuilder : InMemoryAuthenticationServiceBuilderUser,
        InMemoryAuthenticationServiceBuilderBuild {

        override fun build(): InMemoryAuthenticationService {
            return InMemoryAuthenticationService(this)
        }

        private val credentialsByPrinciple: MutableMap<Principal, Credentials> = HashMap()

        override fun withUser(user: User): InMemoryAuthenticationServiceBuilderBuild {
            this.credentialsByPrinciple.putIfAbsent(user.principal, user.credentials)
            return self()
        }

        private fun self(): InMemoryAuthenticationServiceBuilder {
            return this
        }

    }

    interface InMemoryAuthenticationServiceBuilderUser {
        fun withUser(user: User): InMemoryAuthenticationServiceBuilderBuild
    }

    interface InMemoryAuthenticationServiceBuilderBuild : InMemoryAuthenticationServiceBuilderUser {
        fun build(): InMemoryAuthenticationService
    }

}
