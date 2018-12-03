package mc.oauth2.config

import mc.oauth2.Credentials
import mc.oauth2.Principal
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


const val TEST_USERNAME = "my-principal"
const val TEST_PASSWORD = "my-secret"

const val TEST_IP= "127.0.0.1"

val TEST_PRINCIPAL = Principal.valueOf(TEST_USERNAME)
val TEST_CREDENTIALS = Credentials.valueOf(TEST_PASSWORD)
val TEST_ROLES = getValidAuthenticationDesiredRoles()


private fun getValidAuthenticationDesiredRoles(): Collection<GrantedAuthority> {
    return arrayListOf(SimpleGrantedAuthority("ROLE_USER"))
}

class TestData
