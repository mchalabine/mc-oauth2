package mc.oauth2

object McOAuth2AuthorizationServerConstants {
    val ANT_MATCHERS = arrayOf("/login", "/authorize")
    val ROLES_SUPPORTED: Array<String>
        get() = arrayOf(ROLES_USER, ROLES_ADMIN)
    const val ROLES_ADMIN: String = "ADMIN"
    const val ROLES_USER: String = "USER"
}