package mc.oauth2.app

import mc.oauth2.config.web.configurations.AuthorizationServerConfiguration
import mc.oauth2.config.web.configurations.AuthorizationServerSecurityConfiguration
import org.springframework.context.annotation.Import

@Import(*[AuthorizationServerSecurityConfiguration::class, AuthorizationServerConfiguration::class])
class ApplicationConfiguration {

}
