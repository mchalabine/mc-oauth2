package mc.oauth2.config

/**
 * @author Michael Chalabine
 */
class Principal private constructor() {

    private lateinit var principal: String

    class PrincipalBuilder : PrincipalBuilderPrincipal,
        PrincipalBuilderBuild {

        lateinit var principle: String

        override fun withPrincipal(value: String): PrincipalBuilderBuild {
            this.principle = value
            return self()
        }

        private fun self(): PrincipalBuilder {
            return this
        }

        override fun build(): Principal {
            return Principal(this)
        }
    }

    private constructor(builder: PrincipalBuilder) : this() {
        this.principal = builder.principle
    }

    companion object {
        fun valueOf(principal: String): Principal {
            return aPrincipal().withPrincipal(principal).build()
        }

        private fun aPrincipal(): PrincipalBuilderPrincipal {
            return PrincipalBuilder()
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
