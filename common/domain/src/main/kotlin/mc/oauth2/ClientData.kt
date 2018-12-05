package mc.oauth2

/**
 * @author Michael Chalabine
 */
data class ClientData private constructor(val clientId: ClientId) {

    private constructor(builder: ClientDataBuilder) : this(clientId = builder.clientId)

    companion object {
        fun aClientData() : ClientDataBuilderClientId {
            return ClientDataBuilder()
        }
    }

    internal class ClientDataBuilder : ClientDataBuilderClientId, ClientDataBuilderBuild {

        internal lateinit var clientId: ClientId

        override fun build(): ClientData {
            return ClientData(this)
        }

        override fun withClientId(clientId: ClientId): ClientDataBuilderBuild {
            this.clientId = clientId
            return self()
        }

        private fun self(): ClientDataBuilder {
            return this
        }

    }

    interface ClientDataBuilderClientId {
        fun withClientId(clientId: ClientId): ClientDataBuilderBuild
    }

    interface ClientDataBuilderBuild {
        fun build(): ClientData
    }

}
