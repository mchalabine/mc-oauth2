package mc.oauth2.configurations

import io.mockk.every
import io.mockk.mockk
import mc.oauth2.Profiles
import mc.oauth2.config.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.context.support.GenericApplicationContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.context.WebApplicationContext
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices


@ActiveProfiles(Profiles.TEST, Profiles.IN_MEM)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerConfiguration::class,
    AuthorizationServerTestConfiguration::class])
internal class AuthorizationServerConfigurationUnitTest(
        private val applicationContext: WebApplicationContext) {

    val passwordEncoder: PasswordEncoder = getPasswordEncoderBean()

    val clientDetailsService: ClientDetailsService = getClientDetailsServiceBean()

    val tokenStore: TokenStore = getTokenStoreBean()

    val tokenservice: AuthorizationServerTokenServices = getAuthorizationServerTokenServices()

    @Test
    fun `assert context forbids bean override`() {
        val actual = getIsBeanOverrideAllowed()
        assertThat(actual).isFalse()
    }

    @Test
    fun `assert can load context`() {
        assertThat(applicationContext).isNotNull
    }

    @Test
    fun `test is set PasswordEncoder`() {
        val isSet = decideIsPasswordEncoderSet()
        assertThat(isSet).isTrue()
    }

    @Test
    fun `test is set ClientDetailsService`() {
        val details = clientDetailsService.loadClientByClientId(TEST_CLIENT_NAME)
        assertThat(details.clientId).isEqualTo(TEST_CLIENT_NAME)
    }

    @Test
    fun `test is set TokenStore`() {
        val authentication= getValidOAuth2AuthenticationToken()
        val expected = stageTokenStore(authentication)
        val actual = tokenStore.getAccessToken(authentication)
        assertThat(actual).isEqualTo(expected)
    }

    private fun stageTokenStore(
            authentication: OAuth2Authentication): OAuth2AccessToken {
        return tokenservice.createAccessToken(authentication)
    }

    private fun getValidOAuth2AuthenticationToken(): OAuth2Authentication {
        val clientDetails = getTestClientDetails()
        val request = getRequest(clientDetails)
        val token = getAuthenticationToken(clientDetails)
        return OAuth2Authentication(request, token)
    }

    private fun getTestClientDetails(): ClientDetails {
        return clientDetailsService.loadClientByClientId(TEST_CLIENT_NAME)
    }

    private fun getAuthenticationToken(
            clientDetails: ClientDetails): Authentication {
        return UsernamePasswordAuthenticationToken(TEST_PRINCIPAL,
                null, clientDetails.authorities)
    }

    private fun getRequest(clientDetails: ClientDetails): OAuth2Request {
        return OAuth2Request(emptyMap(), TEST_CLIENT_NAME, TEST_ROLES,
                true, clientDetails.scope, clientDetails.resourceIds,
                null, emptySet(), emptyMap())
    }

    private fun getIsBeanOverrideAllowed(): Boolean {
        val beanFactory = getBeanFactory()
        return beanFactory.isAllowBeanDefinitionOverriding
    }

    private fun decideIsPasswordEncoderSet() =
            passwordEncoder.matches(TEST_PASSWORD, TEST_PASSWORD_ENCRYPTED)

    private fun getPasswordEncoderBean(): PasswordEncoder {
        return applicationContext.getBean("passwordEncoder",
                PasswordEncoder::class.java)
    }

    private fun getClientDetailsServiceBean(): ClientDetailsService {
        return applicationContext.getBean("clientDetailsService",
                ClientDetailsService::class.java)
    }

    private fun getBeanFactory() =
            (applicationContext as GenericApplicationContext).defaultListableBeanFactory

    private fun getAuthorizationServerTokenServices(): AuthorizationServerTokenServices {
        return applicationContext.getBean("defaultAuthorizationServerTokenServices",
                AuthorizationServerTokenServices::class.java)
    }

    private fun getTokenStoreBean(): TokenStore {
        return applicationContext.getBean("tokenStore", TokenStore::class.java)
    }
}

@TestConfiguration
@Profile(Profiles.TEST)
@EnableWebSecurity
class AuthorizationServerTestConfiguration {

    @Bean
    fun delegatingClientDetailsService(): ClientDetailsService {
        val service = mockk<ClientDetailsService>()
        return stageClientDetailsService(service)
    }

    private fun stageClientDetailsService(
            service: ClientDetailsService): ClientDetailsService {
        every { service.loadClientByClientId(any()) }
                .returns(getValidTestClientDetails())
        return service
    }

    private fun getValidTestClientDetails(): ClientDetails {
        val details = BaseClientDetails()
        return stageValidTestClientDetails(details)
    }

    private fun stageValidTestClientDetails(
            details: BaseClientDetails): ClientDetails {
        details.clientId = TEST_CLIENT_NAME
        details.authorities = TEST_ROLES
        details.clientSecret = ""
        return details
    }

}