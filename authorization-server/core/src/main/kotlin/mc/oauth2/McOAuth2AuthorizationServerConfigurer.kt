package mc.oauth2

import mc.oauth2.Profiles.IN_MEM
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@Configuration
@EnableAuthorizationServer
@EnableAutoConfiguration
@Import(McOAuth2AuthorizationServerSecurityConfigurer::class)
@Profile(IN_MEM)
class McOAuth2AuthorizationServerConfigurer : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        super.configure(clients)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        super.configure(endpoints)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        super.configure(security)
    }
}
