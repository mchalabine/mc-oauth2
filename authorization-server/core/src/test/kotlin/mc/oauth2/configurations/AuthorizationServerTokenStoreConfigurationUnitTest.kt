package mc.oauth2.configurations

import mc.oauth2.Profiles
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(Profiles.TEST, Profiles.IN_MEM)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerTokenStoreConfiguration::class])
internal class AuthorizationServerTokenStoreConfigurationUnitTest constructor(
        val applicationContext: ApplicationContext) {

    val tokenStore : TokenStore = getTokenStreamBean()

    @Test
    fun `test can retrieve tokenStore bean`() {
        assertThat(tokenStore).isNotNull
    }

    @Test
    fun `test tokenStore bean has expected type`() {
        assertThat(tokenStore).isInstanceOf(InMemoryTokenStore::class.java)
    }

    private fun getTokenStreamBean() = applicationContext.getBean(TokenStore::class.java)

}