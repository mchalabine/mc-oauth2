package mc.oauth2.config

/**
 * @author Michael Chalabine
 */
class User private constructor() {

    lateinit var principal: Principal
    lateinit var credentials: Credentials

    companion object {
        fun aUser(): UserBuilderPrincipal {
            return UserBuilder()
        }
    }

    class UserBuilder : UserBuilderPrincipal, UserBuilderBuild, UserBuilderCredentials {

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

    private constructor(builder: UserBuilder) : this() {
        this.principal = builder.principal
        this.credentials = builder.credentials
    }

    interface UserBuilderPrincipal : UserBuilderBuild {
        fun withPrincipal(principal: String): UserBuilderCredentials
    }

    interface UserBuilderBuild {
        fun build(): User
    }

    interface UserBuilderCredentials {
        fun withCredentials(credentials: String): UserBuilderBuild
    }

}