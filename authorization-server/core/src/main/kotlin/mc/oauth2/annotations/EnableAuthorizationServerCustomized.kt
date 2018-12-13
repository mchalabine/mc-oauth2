package mc.oauth2.annotations

import mc.oauth2.configurations.AuthorizationServerSecurityConfigurationCustomized
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportSelector
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.type.AnnotationMetadata
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import kotlin.reflect.KClass


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
//@ComponentScan(
//        basePackages = ["org.springframework.security.oauth2.provider"],
//        excludeFilters = [
//            ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//                    value = [ClientDetailsService::class])])
@Import(*[AuthorizationServerSecurityConfigurationCustomized::class])
annotation class EnableAuthorizationServerCustomized



