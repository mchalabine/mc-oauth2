package mc.oauth2.config.web.configurations

import mc.oauth2.Profiles
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

/**
 * @author Michael Chalabine
 */
@Configuration
@Import(McOAuth2AuthorizationServerSecurityConfiguration::class)
@Profile(Profiles.IN_MEM)
class McOAuth2AuthorizationServerConfiguration