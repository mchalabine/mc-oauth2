package mc.oauth2.integration

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.provider.ClientDetailsService

/**
 * @author Michael Chalabine
 */
class ClientDetailsUserDetailsService constructor(
        private val clientDetailsService: ClientDetailsService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        TODO("not implemented")
    }
}