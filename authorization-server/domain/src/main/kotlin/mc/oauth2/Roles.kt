package mc.oauth2

object Roles {

    const val USER: String = "USER"

    const val USER_OTP: String = "USER_OTP"

    const val ADMIN: String = "ADMIN"

    val ROLES_ALL: Array<String>
        get() = arrayOf(USER, ADMIN)

}
