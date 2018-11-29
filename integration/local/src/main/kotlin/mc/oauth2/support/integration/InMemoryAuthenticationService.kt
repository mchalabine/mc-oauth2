package mc.oauth2.support.integration

import mc.oauth2.User
import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationService
import mc.oauth2.Credentials
import mc.oauth2.Principal
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import mc.oauth2.integration.AuthenticationResult.UNAUTHENTICATED

/**
 * @author Michael Chalabine
 */
class InMemoryAuthenticationService private constructor(
        private val credentialsByPrincipal: Map<Principal, Credentials>) : AuthenticationService {

    private constructor(builder: InMemoryAuthenticationServiceBuilder) : this(
            credentialsByPrincipal = builder.credentialsByPrinciple)

    override fun authenticate(principal: Principal,
                              credentials: Credentials): AuthenticationResult {
        val expectedCredentials = getCredentials(principal)
        return authenticate(credentials, expectedCredentials)
    }

    private fun authenticate(actual: Credentials, expected: Credentials?): AuthenticationResult {
        return when (actual) {
            expected -> AUTHENTICATED
            else -> UNAUTHENTICATED
        }
    }

    private fun getCredentials(principal: Principal): Credentials? {
        return credentialsByPrincipal.getOrDefault(principal, null)
    }

    companion object {
        fun aService(): InMemoryAuthenticationServiceBuilderUser {
            return InMemoryAuthenticationServiceBuilder()
        }
    }

    internal class InMemoryAuthenticationServiceBuilder : InMemoryAuthenticationServiceBuilderUser,
        InMemoryAuthenticationServiceBuilderBuild {

        override fun build(): InMemoryAuthenticationService {
            return InMemoryAuthenticationService(this)
        }

        internal val credentialsByPrinciple: MutableMap<Principal, Credentials> = HashMap()

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
