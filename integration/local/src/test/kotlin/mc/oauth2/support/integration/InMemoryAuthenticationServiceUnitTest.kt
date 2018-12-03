package mc.oauth2.support.integration

import mc.oauth2.Credentials
import mc.oauth2.Principal
import mc.oauth2.User
import mc.oauth2.config.TEST_CREDENTIALS
import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_PRINCIPAL
import mc.oauth2.config.TEST_USERNAME
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class InMemoryAuthenticationServiceUnitTest {

    private val authenticationService: InMemoryAuthenticationService = getInMemoryService()

    private val principal: Principal = TEST_PRINCIPAL

    private val credentials: Credentials = TEST_CREDENTIALS

    @Test
    fun `test authenticates where both username and credentials match`() {
        val actual = authenticationService.authenticate(principal, credentials)
        assertThat(actual).isSameAs(AUTHENTICATED)
    }

    @Test
    fun `test fails to authenticates where username does not match`() {
        val actual = authenticationService.authenticate(principal, credentials)
        assertThat(actual).isSameAs(AUTHENTICATED)
    }

    private fun getInMemoryService(): InMemoryAuthenticationService {
        return InMemoryAuthenticationService.aService()
                .withUser(User.aUser()
                        .withPrincipal(TEST_USERNAME)
                        .withCredentials(TEST_PASSWORD)
                        .build())
                .build()
    }

}
