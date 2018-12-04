package mc.oauth2.config.web.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

/**
 * Allows to override the default Spring Authorization Server Security set by the
 * [EnableAuthorizationServer] configuration annotation.
 *
 * @see EnableAuthorizationServer
 * @author Michael Chalabine
 */
@Configuration
class AuthorizationServerSecurityConfigurationCustom : AuthorizationServerSecurityConfiguration() {

    override fun configure(http: HttpSecurity) {
        super.configure(http)
    }
}
