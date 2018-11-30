package mc.oauth2.support.integration

import mc.oauth2.Credentials
import mc.oauth2.Principal
import mc.oauth2.User
import mc.oauth2.config.*
import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationResult.AUTHENTICATED
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InMemoryAuthenticationServiceUnitTest {

    private lateinit var authenticationService: InMemoryAuthenticationService

    private val principal: Principal = TEST_PRINCIPAL

    private val credentials: Credentials = TEST_CREDENTIALS

    @BeforeEach
    fun setUp() {
        authenticationService = InMemoryAuthenticationService.aService()
                .withUser(User.aUser()
                        .withPrincipal(TEST_USERNAME)
                        .withCredentials(TEST_PASSWORD)
                        .build())
                .build()
    }

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

}
