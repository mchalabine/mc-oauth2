package mc.oauth2.annotations

import mc.oauth2.configurations.AuthorizationServerSecurityConfigurationCustomized
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Tweaks Spring's default Authorization Server Configuration (i.e. configuration of
 * the authorization and token endpoints) set by the [EnableAuthorizationServer].
 * Imports a customized Security Authorization Server Configuration instead.
 *
 *
 * @see EnableAuthorizationServer
 * @author Michael Chalabine
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthorizationServerEndpointsConfiguration::class,
        AuthorizationServerSecurityConfigurationCustomized::class)
annotation class EnableAuthorizationServerCustomized