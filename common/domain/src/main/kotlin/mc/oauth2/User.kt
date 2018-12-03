package mc.oauth2

/**
 * @author Michael Chalabine
 */
data class User private constructor(val principal: Principal,
                                    val credentials: Credentials) {

    private constructor(builder: UserBuilder) : this(principal = builder.principal,
            credentials = builder.credentials)

    companion object {
        fun aUser(): UserBuilderPrincipal {
            return UserBuilder()
        }
    }

    private class UserBuilder : UserBuilderPrincipal,
        UserBuilderBuild, UserBuilderCredentials {

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