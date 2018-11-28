package mc.oauth2.config.web.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * @author Michael Chalabine
 */
@Configuration
@Import(McOAuth2AuthorizationServerSecurityConfigurer::class)
class McOAuth2AuthorizationServerConfiguration