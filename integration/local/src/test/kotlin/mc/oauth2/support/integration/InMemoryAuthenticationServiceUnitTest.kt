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

    private val principal: Principal = Principal.valueOf(USERNAME)

    private val credentials: Credentials = Credentials.valueOf(PASSWORD)

    @BeforeEach
    fun setUp() {
        authenticationService = InMemoryAuthenticationService.aService()
                .withUser(User.aUser()
                        .withPrincipal(USERNAME)
                        .withCredentials(PASSWORD)
                        .build())
                .build()
    }

    @Test
    fun testAuthenticatesWhereUsernameAndCredentialsMatches() {
        val actual: AuthenticationResult =
                authenticationService.authenticate(principal, credentials)
        assertThat(actual).isSameAs(AUTHENTICATED)
    }
}
