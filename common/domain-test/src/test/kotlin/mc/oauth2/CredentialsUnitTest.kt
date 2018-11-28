package mc.oauth2

import mc.oauth2.config.USERNAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CredentialsUnitTest {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Credentials.valueOf(USERNAME)
        val expected = USERNAME
        assertThat(actual.toString()).isSameAs(expected)
    }

}
