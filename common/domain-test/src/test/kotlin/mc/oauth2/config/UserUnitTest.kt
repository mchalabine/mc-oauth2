package mc.oauth2.config

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class UserUnitTest {

    @Test
    fun testCreate() {
        assertNotNull(User.aUser().withPrincipal("test").withCredentials("test").build())
    }

    @Test
    fun testInitBlock() {
        val user = User.aUser().withPrincipal(USERNAME).withCredentials(PASSWORD).build()
        Assertions.assertThat(user.principal.toString()).isSameAs(USERNAME)
    }
}

