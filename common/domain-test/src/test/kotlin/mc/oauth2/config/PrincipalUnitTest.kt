package mc.oauth2.config

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

internal class PrincipalUnitTest {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Principal.valueOf(USERNAME)
        val expected = USERNAME
        assertThat(actual.toString()).isSameAs(expected)
    }
}
