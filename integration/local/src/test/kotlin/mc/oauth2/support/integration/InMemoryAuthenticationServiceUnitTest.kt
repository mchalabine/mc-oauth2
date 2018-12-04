package mc.oauth2.support.integration

import mc.oauth2.Credentials
import mc.oauth2.Principal
import mc.oauth2.User
import mc.oauth2.config.TEST_CREDENTIALS
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_PRINCIPAL
import mc.oauth2.config.TEST_USERNAME
import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import mc.oauth2.integration.AuthenticationResult.UNAUTHENTICATED
import mc.oauth2.integration.InMemoryAuthenticationService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class InMemoryAuthenticationServiceUnitTest {

    private val authenticationService: InMemoryAuthenticationService = getInMemoryService()

    private val principal: Principal = TEST_PRINCIPAL

    private val credentials: Credentials = TEST_CREDENTIALS

    @Test
    fun `test authenticates where username and credentials match`() {
        val actual = authenticationService.authenticate(principal, credentials)
        assertAuthenticated(actual)
    }

    @Test
    fun `test authentication fails where username does not match`() {
        val principal = getInvalidPrincipal()
        val actual = authenticationService.authenticate(principal, credentials)
        assertUnauthenticated(actual)
    }

    private fun assertAuthenticated(actual: AuthenticationResult) {
        assertThat(actual).isSameAs(AUTHENTICATED)
    }

    private fun assertUnauthenticated(actual: AuthenticationResult) {
        assertThat(actual).isSameAs(UNAUTHENTICATED)
    }

    private fun getInvalidPrincipal() = Principal.valueOf("invalid")

    private fun getInMemoryService(): InMemoryAuthenticationService {
        return InMemoryAuthenticationService.aService()
                .withUser(User.aUser()
                        .withPrincipal(TEST_USERNAME)
                        .withCredentials(TEST_PASSWORD)
                        .build())
                .build()
    }

}
