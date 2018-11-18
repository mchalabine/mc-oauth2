package mc.oauth2

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import kotlin.reflect.full.isSubclassOf

/**
 * @author Michael Chalabine
 */
class McOAuth2AuthenticationProvider(val service: AuthenticationService) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        TODO("not implemented")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication::class.isSubclassOf(UsernamePasswordAuthenticationToken::class)
    }

}
