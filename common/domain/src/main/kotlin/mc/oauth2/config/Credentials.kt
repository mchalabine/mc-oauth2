package mc.oauth2.config

/**
 * @author Michael Chalabine
 */
data class Credentials private constructor(private val builder: CredentialsBuilder) {

    private val credentials: String

    init {
        this.credentials = builder.credentials
    }

    companion object {

        fun valueOf(credentials: String): Credentials {
            return aCredentials().withCredentials(credentials).build()
        }

        private fun aCredentials(): CredentialsBuilderCredentials {
            return CredentialsBuilder()
        }
    }

    private class CredentialsBuilder : CredentialsBuilderCredentials, CredentialsBuilderBuild {

        internal lateinit var credentials: String

        override fun withCredentials(credentials: String): CredentialsBuilderBuild {
            this.credentials = credentials
            return self()
        }

        private fun self(): CredentialsBuilder {
            return this
        }

        override fun build(): Credentials {
            return Credentials(this)
        }
    }

    interface CredentialsBuilderCredentials {
        fun withCredentials(credentials: String): CredentialsBuilderBuild
    }

    interface CredentialsBuilderBuild {
        fun build(): Credentials
    }

    override fun toString(): String {
        return "$credentials"
    }

}
