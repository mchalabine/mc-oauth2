package mc.oauth2.configurations

import mc.oauth2.Profiles
import mc.oauth2.integration.ClientDataService
import mc.oauth2.integration.InMemoryClientDataService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * @author Michael Chalabine
 */
@Configuration
@Profile(Profiles.IN_MEM)
class ClientDataServiceConfiguration {

    @Bean
    fun clientDataService(): ClientDataService {
        return InMemoryClientDataService()
    }

}
