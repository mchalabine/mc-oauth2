package mc.oauth2

import mc.oauth2.config.web.configurations.AuthorizationServerSecurityConfigurationCustom
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Michael Chalabine
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthorizationServerEndpointsConfiguration::class,
        AuthorizationServerSecurityConfigurationCustom::class)
annotation class EnableAuthorizationServerCustom {
}