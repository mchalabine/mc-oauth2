package mc.oauth2.config

class Credentials private constructor() {

    private lateinit var credentials: String

    companion object {

        fun valueOf(credentials: String): Credentials {
            return aCredentials().withCredentials(credentials).build()
        }

        private fun aCredentials(): CredentialsBuilderCredentials {
            return CredentialsBuilder()
        }

    }

    class CredentialsBuilder : CredentialsBuilderCredentials,
        CredentialsBuilderBuild {
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

    private constructor(builder: CredentialsBuilder) : this() {
        this.credentials = builder.credentials
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
