package mc.oauth2.config

/**
 * @author Michael Chalabine
 */
data class User private constructor(private val builder: UserBuilder) {

    val principal: Principal

    val credentials: Credentials

    companion object {
        fun aUser(): UserBuilderPrincipal {
            return UserBuilder()
        }
    }

    init {
        this.principal = builder.principal
        this.credentials = builder.credentials
    }

    private class UserBuilder : UserBuilderPrincipal, UserBuilderBuild, UserBuilderCredentials {

        internal lateinit var principal: Principal
        internal lateinit var credentials: Credentials

        override fun withCredentials(credentials: String): UserBuilderBuild {
            this.credentials = Credentials.valueOf(credentials)
            return self()
        }

        override fun withPrincipal(principal: String): UserBuilderCredentials {
            this.principal = Principal.valueOf(principal)
            return self()
        }

        override fun build(): User {
            return User(this)
        }

        private fun self(): UserBuilder {
            return this
        }
    }

    interface UserBuilderPrincipal : UserBuilderBuild {
        fun withPrincipal(principal: String): UserBuilderCredentials
    }

    interface UserBuilderCredentials {
        fun withCredentials(credentials: String): UserBuilderBuild
    }

    interface UserBuilderBuild {
        fun build(): User
    }

}