package mc.oauth2

import mc.oauth2.config.TEST_PASSWORD
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

internal class PrincipalUnitTest {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Principal.valueOf(TEST_PASSWORD)
        val expected = TEST_PASSWORD
        assertThat(actual.toString()).isSameAs(expected)
    }

    @Test
    fun `test equal principals are equal`() {
        val p1 = Principal.valueOf("test")
        val p2 = Principal.valueOf("test")
        assertThat(p1).isEqualTo(p2)
    }
}
