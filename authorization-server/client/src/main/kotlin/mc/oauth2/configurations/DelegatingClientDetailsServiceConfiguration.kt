package mc.oauth2.configurations

import mc.oauth2.Profiles
import mc.oauth2.integration.DelegatingClientDetailsService
import mc.oauth2.integration.ClientDataService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.provider.ClientDetailsService

/**
 * @author Michael Chalabine
 */
@Configuration
@Profile(Profiles.LIVE)
@Import(ClientDataServiceConfiguration::class)
class DelegatingClientDetailsServiceConfiguration(
        private val clientDataService: ClientDataService) {

    @Bean
    fun clientDetailsService(): ClientDetailsService {
        return DelegatingClientDetailsService(clientDataService)
    }

}
