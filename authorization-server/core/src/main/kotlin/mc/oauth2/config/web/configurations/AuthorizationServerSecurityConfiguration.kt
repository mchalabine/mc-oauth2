package mc.oauth2.config.web.configurations

import mc.oauth2.*
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.MethodInvocationInfo
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author Michael Chalabine
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
@Import(DelegatingAuthenticationProviderConfiguration::class)
class AuthorizationServerSecurityConfiguration(
        private val userAuthenticationProvider: AuthenticationProvider) :
    WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        configureUserAuthenticationProvider(auth)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        secureEndpointsUsingSpringSecurity(http)
    }

    private fun secureEndpointsUsingSpringSecurity(
            http: HttpSecurity) {
        init(http)
        configureCsrf(http)
        configureUrisOpenForAll(http)
        configureUrisRestrictedToAdmin(http)
        configureEveryOtherUri(http)
        configureLoginForm(http)
        configureLogout(http)
        configureErrorHandling(http)
        configureHeaders(http)
    }

    private fun configureUserAuthenticationProvider(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(userAuthenticationProvider)
    }

    private fun init(http: HttpSecurity) {
        http.authorizeRequests()
    }

    private fun configureCsrf(http: HttpSecurity) {
        val configurer = getCsrfConfigurer(http)
        liftCsrfProtectionForOAuth2AuthorizationEndpoint(configurer)
    }

    private fun liftCsrfProtectionForOAuth2AuthorizationEndpoint(
            csrfConfigurer: CsrfConfigurer<HttpSecurity>) {
        val path = getOAuth2AuthorizationEndpointPath()
        liftCsrfProtectionForPath(csrfConfigurer, path)
    }

    private fun liftCsrfProtectionForPath(configurer: CsrfConfigurer<HttpSecurity>, path: String) {
        configurer.ignoringAntMatchers(path)
    }

    private fun getCsrfConfigurer(http: HttpSecurity) = http.csrf()

    private fun getOAuth2AuthorizationEndpointPath(): String {
        val builder = getEmptyUriComponentsBuilder()
        return getOAuth2AuthorizationEndpointPath(builder)
    }

    private fun getEmptyUriComponentsBuilder(): UriComponentsBuilder {
        return UriComponentsBuilder.fromPath("/")
    }

    private fun getOAuth2AuthorizationEndpointPath(uriBuilder: UriComponentsBuilder): String {
        val metaData = getOAuth2AuthorizationEndpointMvcMetaData()
        return getOAuth2AuthorizationEndpointPath(uriBuilder, metaData)
    }

    private fun getOAuth2AuthorizationEndpointPath(uriBuilder: UriComponentsBuilder,
                                                   mvcMetaData: MethodInvocationInfo): String {
        return MvcUriComponentsBuilder.fromMethodCall(uriBuilder, mvcMetaData).build().path!!
    }

    private fun getOAuth2AuthorizationEndpointMvcMetaData(): MethodInvocationInfo {
        val mock = getOAuth2AuthorizationEndpointMock()
        return extractOAuth2AuthorizationEndpointMvcMetaData(mock)
    }

    private fun extractOAuth2AuthorizationEndpointMvcMetaData(
            mock: AuthorizationEndpoint): MethodInvocationInfo {
        val result = triggerAuthorize(mock)
        return toMvcMetaData(result)
    }

    private fun triggerAuthorize(mock: AuthorizationEndpoint): ModelAndView =
            mock.authorize(null, null, null, null)

    private fun toMvcMetaData(modelAndView: ModelAndView): MethodInvocationInfo {
        require(modelAndView is MethodInvocationInfo)
        return modelAndView as MethodInvocationInfo
    }

    private fun getOAuth2AuthorizationEndpointMock() =
            MvcUriComponentsBuilder.on(AuthorizationEndpoint::class.java)

    private fun configureLogout(http: HttpSecurity) {
        http.logout().permitAll()
    }

    private fun configureLoginForm(http: HttpSecurity) {
        http.formLogin()
                .loginPage(URI_LOGIN)
                .loginProcessingUrl(URI_LOGIN)
                .failureUrl(URI_LOGIN_ERROR)
    }

    private fun configureEveryOtherUri(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().authenticated()
    }

    private fun configureErrorHandling(http: HttpSecurity) {
        http.exceptionHandling()
                .accessDeniedPage(URI_LOGIN)
                .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint(URI_LOGIN))
    }

    private fun configureHeaders(http: HttpSecurity) {
        http.headers()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN)
                .and()
                .httpStrictTransportSecurity().maxAgeInSeconds(MAX_DURATION)
    }

    private fun configureUrisRestrictedToAdmin(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(*URIS_ADMIN_ONLY).hasRole(Roles.ADMIN)
    }

    private fun configureUrisOpenForAll(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(*URIS_ALLOWED).permitAll()
    }

}
