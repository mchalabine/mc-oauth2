package mc.oauth2.config

import mc.oauth2.config.Principal
import mc.oauth2.config.USERNAME
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class PrincipalUnitTest {

    @Test
    fun testToStringReturnsExpected() {
        val actual = Principal.valueOf(USERNAME)
        val expected = USERNAME
        assertThat(actual.toString(), `is`(expected))
    }
}
