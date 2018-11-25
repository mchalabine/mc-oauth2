package mc.oauth2.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class UserUnitTest {

    @Test
    fun testCreate() {
        assertNotNull(User.aUser().withPrincipal("test").withCredentials("test").build())
    }
}

