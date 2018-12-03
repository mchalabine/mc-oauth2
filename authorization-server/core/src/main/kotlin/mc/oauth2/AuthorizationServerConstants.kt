package mc.oauth2

import java.time.Duration

internal const val URI_LOGIN: String = "/login"

internal const val URI_LOGIN_ALL: String = "$URI_LOGIN**"

internal const val URI_LOGIN_ERROR: String = "/login?error"

internal const val ROLE_ADMIN: String = "ADMIN"

internal const val ROLE_USER: String = "USER"

internal const val MSG_AUTHENTICATION_FAILURE = "Authentication failed"

internal val URIS_ALLOWED = arrayOf(URI_LOGIN_ALL, "/resources/**", "/signup", "/about")

internal val URIS_PROTECT = arrayOf("/authorize")

internal val URIS_ADMIN_ONLY = arrayOf("/admin")

internal val ROLES_ALL: Array<String>
    get() = arrayOf(ROLE_USER, ROLE_ADMIN)

internal val MAX_DURATION = Duration.ofDays(365).seconds


