package mc.oauth2.configurations

import io.mockk.every
import io.mockk.mockk
import mc.oauth2.MSG_AUTHENTICATION_FAILURE
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
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles(Profiles.TEST, Profiles.IN_MEM)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [AuthorizationServerConfiguration::class,
    AuthorizationServerTestConfiguration::class])
internal class AuthorizationServerConfigurationUnitTest(
        private val applicationContext: WebApplicationContext) {

    val passwordEncoder: PasswordEncoder = getPasswordEncoderBean()
    val clientDetailsService: ClientDetailsService = getClientDetailsServiceBean()

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
    fun `test properly sets password encoder`() {
        val isSet = decideIsPasswordEncoderSet()
        assertThat(isSet).isTrue()
    }

    @Test
    fun `test properly sets client details service`() {
        val actual = clientDetailsService.loadClientByClientId(TEST_CLIENT_NAME)
        assertThat(actual.clientId).isEqualTo(TEST_CLIENT_NAME)
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
}

@TestConfiguration
@Profile(Profiles.TEST)
@EnableWebSecurity
class AuthorizationServerTestConfiguration {

    @Bean
    fun userAuthenticationProvider(): AuthenticationProvider {
        val provider = mockk<AuthenticationProvider>()
        return stageAuthenticationProvider(provider)
    }

    @Bean
    fun delegatingClientDetailsService(): ClientDetailsService {
        val service = mockk<ClientDetailsService>()
        return stageClientDetailsService(service)
    }

    private fun stageAuthenticationProvider(
            provider: AuthenticationProvider): AuthenticationProvider {
        stageAuthenticate(provider)
        stageSupports(provider)
        return provider
    }

    private fun stageAuthenticate(provider: AuthenticationProvider) {
        stageAuthenticateFailure(provider)
        stageAuthenticateSuccess(provider)
    }

    private fun stageAuthenticateSuccess(provider: AuthenticationProvider) {
        every { provider.authenticate(getValidAuthentication()) }
                .returns(getValidAuthenticationToken())
    }

    private fun stageAuthenticateFailure(provider: AuthenticationProvider) {
        every { provider.authenticate(any()) }
                .throws(AuthenticationServiceException(MSG_AUTHENTICATION_FAILURE))
    }

    private fun getValidAuthentication(): Authentication {
        val token = UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD)
        token.details = WebAuthenticationDetails(MockHttpServletRequest())
        return token
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

    private fun getValidAuthenticationToken(): Authentication =
            UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD, TEST_ROLES)

    private fun stageSupports(provider: AuthenticationProvider) {
        every { provider.supports(any()) }.returns(true)
    }
}