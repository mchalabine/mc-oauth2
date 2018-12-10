package mc.oauth2.annotations

import mc.oauth2.configurations.AuthorizationServerSecurityConfigurationCustomized
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

/**
 * Tweaks Spring's default Authorization Server Configuration -- configuration of
 * the authorization and token endpoints.
 *
 * @see EnableAuthorizationServer
 * @see EnableAuthorizationServerCustomized
 *
 * @author Michael Chalabine
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(*[AuthorizationServerEndpointsConfiguration::class,
    AuthorizationServerSecurityConfigurationCustomized::class])
annotation class EnableAuthorizationServerCustomized