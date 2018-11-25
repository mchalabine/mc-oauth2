package mc.oauth2.config

import mc.oauth2.config.Profiles.IN_MEM
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@Configuration
@Import(McOAuth2AuthorizationServerSecurityConfigurer::class)
@Profile(IN_MEM)
class McOAuth2AuthorizationServerConfigurer : AuthorizationServerConfigurerAdapter() {

}

