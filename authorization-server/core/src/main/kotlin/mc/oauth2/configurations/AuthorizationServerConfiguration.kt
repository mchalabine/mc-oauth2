package mc.oauth2.configurations

import mc.oauth2.annotations.EnableAuthorizationServerCustomized
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.token.TokenStore

/**
 * @author Michael Chalabine
 */
@Configuration
@EnableAuthorizationServerCustomized
@Import(*[
    AuthorizationServerPasswordEncodersConfiguration::class,
    DelegatingClientDetailsServiceConfiguration::class,
    AuthorizationServerTokenStoreConfiguration::class])
class AuthorizationServerConfiguration(
        private val passwordEncoder: PasswordEncoder,
        private val delegatingClientDetailsService: ClientDetailsService,
        private val tokenStore: TokenStore) :
    AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.passwordEncoder(passwordEncoder)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails(delegatingClientDetailsService)
        super.configure(clients)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore)
        super.configure(endpoints)
    }

}