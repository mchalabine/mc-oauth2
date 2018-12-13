package mc.oauth2.configurations

import mc.oauth2.annotations.EnableAuthorizationServerCustomized
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

/**
 * Customizes Spring's internal Authorization Server Security Configuration in a way
 * not achievable through other means, e.g. through a new Security Configuration by
 * extending the [AuthorizationServerConfigurerAdapter].
 *
 * @see EnableAuthorizationServer
 * @see EnableAuthorizationServerCustomized
 * @see AuthorizationServerConfigurerAdapter
 *
 * @author Michael Chalabine
 */
class AuthorizationServerSecurityConfigurationCustomized :
    AuthorizationServerSecurityConfiguration() {

    override fun configure(web: WebSecurity) {
        super.configure(web)
    }
}
