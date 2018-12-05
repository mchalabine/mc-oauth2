package mc.oauth2.app

import mc.oauth2.configurations.AuthorizationServerConfiguration
import mc.oauth2.configurations.AuthorizationServerSecurityConfiguration
import org.springframework.context.annotation.Import

@Import(*[AuthorizationServerSecurityConfiguration::class, AuthorizationServerConfiguration::class])
class ApplicationConfiguration
