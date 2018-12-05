package mc.oauth2.integration

import mc.oauth2.ClientData
import mc.oauth2.ClientId
import mc.oauth2.integration.ClientDataService
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService

/**
 * @author Michael Chalabine
 */
class DelegatingClientDetailsService(
        private val clientDataService: ClientDataService) : ClientDetailsService {

    override fun loadClientByClientId(clientId: String): ClientDetails {
        val id = ClientId.valueOf(clientId)
        return toClientDetails(clientDataService.loadClientByClientId(id))
    }

    private fun toClientDetails(clientData: ClientData): ClientDetails {
        TODO("Not yet implemented")
    }
}