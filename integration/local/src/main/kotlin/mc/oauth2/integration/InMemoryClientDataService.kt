package mc.oauth2.integration

import mc.oauth2.ClientData
import mc.oauth2.ClientId

/**
 * @author Michael Chalabine
 */
class InMemoryClientDataService : ClientDataService {

    override fun loadClientByClientId(clientId: ClientId): ClientData {
        TODO("not implemented")
    }
}