package mc.oauth2

import mc.oauth2.config.TEST_USERNAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CredentialsUnitTest {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Credentials.valueOf(TEST_USERNAME)
        val expected = TEST_USERNAME
        assertThat(actual.toString()).isSameAs(expected)
    }

    @Test
    fun `test equal credentials are equal`() {
        val c1 = Credentials.valueOf("test")
        val c2 = Credentials.valueOf("test")
        assertThat(c1).isEqualTo(c2)
    }
}
