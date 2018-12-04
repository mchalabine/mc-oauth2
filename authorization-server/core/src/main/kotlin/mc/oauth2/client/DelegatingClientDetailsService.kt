package mc.oauth2.client

import mc.oauth2.integration.ClientDataService
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService

/**
 * @author Michael Chalabine
 */
class DelegatingClientDetailsService(
        private val clientDataService: ClientDataService) : ClientDetailsService {

    override fun loadClientByClientId(clientId: String): ClientDetails {
        TODO("not implemented")
    }
}