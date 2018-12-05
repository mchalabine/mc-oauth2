package mc.oauth2.integration

import mc.oauth2.ClientData
import mc.oauth2.ClientId

/**
 * @author Michael Chalabine
 */
interface ClientDataService {

    fun loadClientByClientId(clientId: ClientId): ClientData
}