package mc.oauth2

import java.time.Duration

const val URI_LOGIN: String = "/login"

const val URI_LOGIN_ALL: String = "$URI_LOGIN**"

const val URI_LOGIN_ERROR: String = "/login?error"

const val ROLE_ADMIN: String = "ADMIN"

const val ROLE_USER: String = "USER"

val URIS_ALLOWED = arrayOf(URI_LOGIN_ALL, "/resources/**", "/signup", "/about")

val URIS_PROTECT = arrayOf("/authorize")

val URIS_ADMIN_ONLY = arrayOf("/admin")

val ROLES_ALL: Array<String>
    get() = arrayOf(ROLE_USER, ROLE_ADMIN)

val MAX_DURATION = Duration.ofDays(365).seconds


