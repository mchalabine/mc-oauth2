package mc.oauth2.config

import mc.oauth2.config.AuthenticationResult.AUTHENTICATED
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val USERNAME = "my-principal"
private const val PASSWORD = "my-secret"

/**
 * @author Michael Chalabine
 */
@RunWith(JUnit4::class)
class McOAuth2InMemoryAuthenticationServiceUnitTest {

    private lateinit var authenticationService: McOAuth2InMemoryAuthenticationService

    private val principal: Principal = Principal()

    private val credentials: Credentials = Credentials()

    @Before
    fun setUp() {
        authenticationService = McOAuth2InMemoryAuthenticationService()
    }

    @Test
    fun testInstantiate() {
        assertNotNull(authenticationService)
    }

    @Test
    fun testAuthenticatesWhereUsernameAndCredentialsMatches() {
        val actual: AuthenticationResult =
                authenticationService.authenticate(principal, credentials)
        assertThat(actual, `is`(AUTHENTICATED))
    }
}
