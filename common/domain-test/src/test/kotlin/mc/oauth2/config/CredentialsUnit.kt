package mc.oauth2.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CredentialsUnit {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Credentials.valueOf(USERNAME)
        val expected = USERNAME
        assertThat(actual.toString()).isSameAs(expected)
    }

}
