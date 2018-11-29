package mc.oauth2.providers

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import mc.oauth2.config.TEST_CREDENTIALS
import mc.oauth2.config.TEST_PRINCIPAL
import mc.oauth2.integration.AuthenticationResult
import mc.oauth2.integration.AuthenticationService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class McOAuth2AuthenticationProviderUnitTest {

    private val authenticationService: AuthenticationService = mockk<AuthenticationService>()

    private val provider = McOAuth2AuthenticationProvider(authenticationService)

    @BeforeEach
    fun beforeEach() {
        every { authenticationService.authenticate(any(), any()) }
                .returns(AuthenticationResult.UNAUTHENTICATED)
        every { authenticationService.authenticate(TEST_PRINCIPAL, TEST_CREDENTIALS) }
                .returns(AuthenticationResult.AUTHENTICATED)
    }

    @AfterEach
    fun afterEach() {
        clearMocks(authenticationService)
    }

    @Test
    fun `test authenticates where principal and credentials match`() {
        val authentication = getValidAuthenticationToken()
        assertAuthenticated(provider.authenticate(authentication))
    }

    @Test
    fun `test rejects where principal match and credentials not`() {
        val authentication = getInvalidAuthenticationToken()
        assertThatThrownBy { provider.authenticate(authentication) }.isInstanceOf(
                AuthenticationException::class.java)
    }

    @Test
    fun `test supports expected authentication token type`() {
        val actual: Boolean = provider.supports(getExpectedAuthenticationTokenType())
        assertThat(actual).isTrue()
    }

    private fun assertAuthenticated(actual: Authentication) {
        assertThat(actual.isAuthenticated).isTrue()
    }

    private fun getValidAuthenticationToken() =
            UsernamePasswordAuthenticationToken(TEST_PRINCIPAL, TEST_CREDENTIALS)

    private fun getInvalidAuthenticationToken() =
            UsernamePasswordAuthenticationToken(TEST_PRINCIPAL, "other")

    private fun getExpectedAuthenticationTokenType(): Class<UsernamePasswordAuthenticationToken> {
        return UsernamePasswordAuthenticationToken::class.java
    }
}
