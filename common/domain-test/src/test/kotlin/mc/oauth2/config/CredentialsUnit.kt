package mc.oauth2.config

import mc.oauth2.config.Credentials.Companion.valueOf
import mc.oauth2.config.PASSWORD
import mc.oauth2.config.USERNAME
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class CredentialsUnit {

    @Test
    fun testToStringReturnsExpected() {
        val actual = valueOf(USERNAME)
        val expected = PASSWORD
        assertThat(actual.toString(), `is`(expected))
    }

}
