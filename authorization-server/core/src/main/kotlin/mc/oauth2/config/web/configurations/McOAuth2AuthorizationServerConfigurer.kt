package mc.oauth2.config.web.configurations

import mc.oauth2.Profiles.IN_MEM
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter

@Configuration
@Import(McOAuth2AuthorizationServerSecurityConfigurer::class)
@Profile(IN_MEM)
class McOAuth2AuthorizationServerConfigurer : AuthorizationServerConfigurerAdapter() {

}

