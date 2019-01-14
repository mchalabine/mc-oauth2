package mc.oauth2.configurations

import mc.oauth2.Profiles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

/**
 * @author Michael Chalabine
 */
@Configuration
class AuthorizationServerTokenStoreConfiguration {

    @Bean
    fun tokenStore(): TokenStore {
        return InMemoryTokenStore()
    }

}
