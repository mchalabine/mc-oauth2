package mc.oauth2.providers

import mc.oauth2.Credentials
import mc.oauth2.MSG_AUTHENTICATION_FAILURE
import mc.oauth2.Principal
import mc.oauth2.ROLE_USER
import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import mc.oauth2.integration.AuthenticationService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * @author Michael Chalabine
 */
class McOAuth2AuthenticationProvider(
        private val authenticationService: AuthenticationService) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val principal = getPrincipal(authentication)
        val credentials = getCredentials(authentication)
        return authenticate(principal, credentials)
    }

    private fun authenticate(principal: Principal, credentials: Credentials): Authentication {
        val result = authenticationService.authenticate(principal, credentials)
        return authenticate(principal, credentials, result)
    }

    private fun authenticate(principal: Principal, credentials: Credentials,
                             authentication: AuthenticationResult): Authentication {
        validate(authentication)
        return getValidAuthenticationToken(principal, credentials)
    }

    private fun getValidAuthenticationToken(principal: Principal,
                                            credentials: Credentials): Authentication {
        val roles = getValidAuthenticationDesiredRoles()
        return getValidAuthenticationToken(principal, credentials, roles)
    }

    private fun getValidAuthenticationToken(principal: Principal, credentials: Credentials,
                                            roles: Collection<GrantedAuthority>): Authentication {
        return UsernamePasswordAuthenticationToken(principal, credentials, roles)
    }

    private fun getValidAuthenticationDesiredRoles(): Collection<GrantedAuthority> {
        return arrayListOf(SimpleGrantedAuthority("ROLE_$ROLE_USER"))
    }

    private fun validate(authenticationResult: AuthenticationResult) {
        if (!isAuthenticated(authenticationResult)) {
            throwTheOnlyExceptionAllowed()
        }
    }

    private fun throwTheOnlyExceptionAllowed() {
        throwExceptionThatMasksTheActualProblem()
    }

    private fun throwExceptionThatMasksTheActualProblem() {
        throw AuthenticationServiceException(MSG_AUTHENTICATION_FAILURE)
    }

    override fun supports(authenticationJavaClass: Class<*>): Boolean {
        val expected = getSupportedTokenClass()
        val actual = getKotlinClass(authenticationJavaClass)
        return actual.isSubclassOf(expected)
    }

    private fun getPrincipal(authentication: Authentication): Principal {
        val principal = authentication.principal
        return getPrincipal(principal)
    }

    private fun getPrincipal(principal: Any) = getPrincipal(principal.toString())

    private fun getCredentials(credentials: String): Credentials = Credentials.valueOf(credentials)

    private fun getPrincipal(principal: String): Principal = Principal.valueOf(principal)

    private fun getKotlinClass(authentication: Class<*>) = authentication.kotlin

    private fun isAuthenticated(authenticationResult: AuthenticationResult) =
            AUTHENTICATED == authenticationResult

    private fun getCredentials(authentication: Authentication) =
            getCredentials(authentication.credentials.toString())

    private fun getSupportedTokenClass(): KClass<UsernamePasswordAuthenticationToken> {
        return UsernamePasswordAuthenticationToken::class
    }

}
