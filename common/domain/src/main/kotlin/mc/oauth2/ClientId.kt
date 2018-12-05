package mc.oauth2

/**
 * @author Michael Chalabine
 */
data class ClientId private constructor(private val clientId: String) {

    companion object {
        fun valueOf(clientId: String): ClientId {
            return aClientId().withId(clientId).build()
        }

        private fun aClientId(): ClientIdBuilderId {
            return ClientIdBuilder()
        }
    }

    class ClientIdBuilder : ClientIdBuilderId, ClientIdBuilderBuild {

        lateinit var clientId: String

        override fun withId(id: String): ClientIdBuilderBuild {
            this.clientId = id;
            return self()
        }

        private fun self(): ClientIdBuilder {
            return this
        }

        override fun build(): ClientId {
            return ClientId(this)
        }

    }

    constructor(builder: ClientIdBuilder) : this(clientId = builder.clientId)

    interface ClientIdBuilderId {
        fun withId(id: String): ClientIdBuilderBuild
    }

    interface ClientIdBuilderBuild {
        fun build(): ClientId
    }
}