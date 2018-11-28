package mc.oauth2.config.providers

import mc.oauth2.Credentials
import mc.oauth2.Principal
import mc.oauth2.config.ROLE_USER
import mc.oauth2.integration.AuthenticationService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * @author Michael Chalabine
 */
class McOAuth2AuthenticationProvider(private val authenticationService: AuthenticationService) :
    AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val principal = getPrincipal(authentication)
        val credentials = getCredentials(authentication)
        return authenticate(principal, credentials)
    }

    private fun authenticate(p: Principal, c: Credentials): UsernamePasswordAuthenticationToken {
        val authenticationResult = authenticationService.authenticate(p, c)
        return UsernamePasswordAuthenticationToken(p, c,
                arrayListOf(SimpleGrantedAuthority("ROLE_$ROLE_USER")))
    }

    private fun getCredentials(authentication: Authentication) =
            getCredentials(authentication.credentials.toString())

    private fun getPrincipal(authentication: Authentication): Principal {
        val principal = authentication.principal
        return getPrincipal(principal)
    }

    private fun getPrincipal(principal: Any) = getPrincipal(principal.toString())

    private fun getCredentials(credentials: String): Credentials {
        return Credentials.valueOf(credentials)
    }

    private fun getPrincipal(principal: String): Principal {
        return Principal.valueOf(principal)
    }

    override fun supports(authenticationJavaClass: Class<*>): Boolean {
        val expected = getSupportedTokenClass()
        val actual = getKotlinClass(authenticationJavaClass)
        return actual.isSubclassOf(expected)
    }

    private fun getSupportedTokenClass(): KClass<UsernamePasswordAuthenticationToken> {
        return UsernamePasswordAuthenticationToken::class
    }

    private fun getKotlinClass(
            authentication: Class<*>) = authentication.kotlin

}
