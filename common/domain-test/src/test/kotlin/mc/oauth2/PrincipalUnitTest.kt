package mc.oauth2

import mc.oauth2.config.PASSWORD
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

internal class PrincipalUnitTest {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Principal.valueOf(PASSWORD)
        val expected = PASSWORD
        assertThat(actual.toString()).isSameAs(expected)
    }
}
