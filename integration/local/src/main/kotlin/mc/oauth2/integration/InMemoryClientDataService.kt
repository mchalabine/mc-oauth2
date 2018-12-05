package mc.oauth2.integration

import mc.oauth2.ClientData
import mc.oauth2.ClientId

/**
 * @author Michael Chalabine
 */
class InMemoryClientDataService private constructor(
        private val clientDataByClientId: Map<ClientId, ClientData>) : ClientDataService {

    companion object {
        fun aService() : InMemoryClientDataServiceBuilderClientData {
            return InMemoryClientDataServiceBuilder()
        }
    }
    private constructor(builder: InMemoryClientDataServiceBuilder) : this(
            clientDataByClientId = builder.clientDataByClientId)

    override fun loadClientByClientId(clientId: ClientId): ClientData {
        return clientDataByClientId.getValue(clientId)
    }

    internal class InMemoryClientDataServiceBuilder :
        InMemoryClientDataServiceBuilderClientData, InMemoryClientDataServiceBuilderBuild {

        override fun build(): ClientDataService {
            return InMemoryClientDataService(this)
        }

        internal val clientDataByClientId: MutableMap<ClientId, ClientData> = HashMap()

        override fun withClientData(clientData: ClientData): InMemoryClientDataServiceBuilderBuild {
            this.clientDataByClientId.putIfAbsent(clientData.clientId, clientData)
            return self()
        }

        private fun self(): InMemoryClientDataServiceBuilder {
            return this
        }

    }

    interface InMemoryClientDataServiceBuilderClientData {
        fun withClientData(clientData: ClientData) : InMemoryClientDataServiceBuilderBuild
    }

    interface InMemoryClientDataServiceBuilderBuild {
        fun build(): ClientDataService
    }
}