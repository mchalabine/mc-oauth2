package mc.oauth2.annotations

import mc.oauth2.configurations.AuthorizationServerSecurityConfigurationCustom
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Imports Custom Security Authorization Server Configuration compared to Spring's
 * default [EnableAuthorizationServer].
 *
 * @see EnableAuthorizationServer
 * @author Michael Chalabine
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthorizationServerEndpointsConfiguration::class,
        AuthorizationServerSecurityConfigurationCustom::class)
annotation class EnableAuthorizationServerCustomized {
}