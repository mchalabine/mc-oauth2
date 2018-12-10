package mc.oauth2.integration

import mc.oauth2.ClientData
import mc.oauth2.ClientId
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationService
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.stereotype.Service

/**
 * @author Michael Chalabine
 */
@Service
class DelegatingClientDetailsService(
        private val clientDataService: ClientDataService) : ClientDetailsService,
    ClientRegistrationService {

    override fun updateClientSecret(clientId: String?, secret: String?) {
        TODO("not implemented")
    }

    override fun removeClientDetails(clientId: String?) {
        TODO("not implemented")
    }

    override fun addClientDetails(clientDetails: ClientDetails?) {
        TODO("not implemented")
    }

    override fun updateClientDetails(clientDetails: ClientDetails?) {
        TODO("not implemented")
    }

    override fun listClientDetails(): MutableList<ClientDetails> {
        TODO("not implemented")
    }

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
        val clientDetails = BaseClientDetails()
        setClientId(clientData, clientDetails)
        return clientDetails
    }

    private fun setClientId(clientData: ClientData, clientDetails: BaseClientDetails) {
        clientDetails.clientId = clientData.clientId.toString()
    }
}