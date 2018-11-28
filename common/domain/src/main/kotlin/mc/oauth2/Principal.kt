package mc.oauth2

/**
 * @author Michael Chalabine
 */
data class Principal private constructor(private val principal: String) {

    private constructor(builder: PrincipalBuilder) : this(principal = builder.principal)

    companion object {
        fun valueOf(principal: String): Principal {
            return aPrincipal().withPrincipal(principal).build()
        }

        private fun aPrincipal(): PrincipalBuilderPrincipal {
            return PrincipalBuilder()
        }
    }

    private class PrincipalBuilder : PrincipalBuilderPrincipal,
        PrincipalBuilderBuild {

        lateinit var principal: String

        override fun withPrincipal(value: String): PrincipalBuilderBuild {
            this.principal = value
            return self()
        }

        private fun self(): PrincipalBuilder {
            return this
        }

        override fun build(): Principal {
            return Principal(this)
        }
    }

    interface PrincipalBuilderPrincipal {
        fun withPrincipal(value: String): PrincipalBuilderBuild
    }

    interface PrincipalBuilderBuild {
        fun build(): Principal
    }

    override fun toString(): String {
        return "$principal"
    }
}
