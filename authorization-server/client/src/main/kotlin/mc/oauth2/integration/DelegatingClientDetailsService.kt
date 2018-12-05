package mc.oauth2.integration

import mc.oauth2.ClientData
import mc.oauth2.ClientId
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService

/**
 * @author Michael Chalabine
 */
class DelegatingClientDetailsService(
        private val clientDataService: ClientDataService) : ClientDetailsService {

    override fun loadClientByClientId(clientId: String): ClientDetails {
        val clientData = loadClientDataByClientId(clientId)
        return getClientDetails(clientData)
    }

    private fun loadClientDataByClientId(id: String): ClientData {
        val clientId = getClientId(id)
        return clientDataService.loadClientByClientId(clientId)
    }

    private fun getClientId(id: String) = ClientId.valueOf(id)

    private fun getClientDetails(clientData: ClientData): ClientDetails {
        TODO("Not yet implemented")
    }
}