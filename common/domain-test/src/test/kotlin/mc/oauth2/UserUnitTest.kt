package mc.oauth2

import mc.oauth2.config.TEST_PASSWORD
import mc.oauth2.config.TEST_USERNAME
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
        val user = User.aUser().withPrincipal(TEST_USERNAME).withCredentials(
                TEST_PASSWORD).build()
        Assertions.assertThat(user.principal.toString()).isSameAs(TEST_USERNAME)
    }
}

