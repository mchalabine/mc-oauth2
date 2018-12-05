package mc.oauth2.config

import mc.oauth2.ClientData
import mc.oauth2.ClientId
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

const val TEST_CLIENT_NAME = "test-client"
val TEST_CLIENT_ID = ClientId.valueOf(TEST_CLIENT_NAME)
val TEST_CLIENT = ClientData.aClientData().withClientId(TEST_CLIENT_ID).build()


private fun getValidAuthenticationDesiredRoles(): Collection<GrantedAuthority> {
    return arrayListOf(SimpleGrantedAuthority("ROLE_USER"))
}

class TestData
