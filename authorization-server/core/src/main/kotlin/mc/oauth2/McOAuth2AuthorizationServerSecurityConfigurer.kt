package mc.oauth2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter

/**
 * @author Michael Chalabine
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
@Import(McOAuth2AuthorizationServerPasswordEncodersConfigurer::class,
        McOAuth2AuthenticationServiceConfigurer::class)
class McOAuth2AuthorizationServerSecurityConfigurer : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun authenticationProvider(service: AuthenticationService): AuthenticationProvider {
        return exposeProviderAndThusDisableSpringBootAutoConfiguration(service)
    }

    private fun exposeProviderAndThusDisableSpringBootAutoConfiguration(
            service: AuthenticationService): AuthenticationProvider {
        return McOAuth2AuthenticationProvider(service)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("my-principal")
                .password(passwordEncoder.encode("my-secret"))
                .roles(*ROLES_ALL)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        init(http)
        configureCsrf(http)
        configureUrisOpenForAll(http)
        configureUrisRestrictedToAdmin(http)
        configureEveryOtherUri(http)
        configureLoginForm(http)
        configureLogout(http)
        configureErrorHandling(http)
        configureUseOfHeaders(http)
    }

    private fun init(http: HttpSecurity) {
        http.authorizeRequests()
    }

    private fun configureCsrf(http: HttpSecurity) {
        http.csrf()
    }

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

    private fun configureUseOfHeaders(http: HttpSecurity) {
        http.headers()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN).and()
                .httpStrictTransportSecurity().maxAgeInSeconds(MAX_DURATION)
    }

    private fun configureUrisRestrictedToAdmin(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(*URIS_ADMIN_ONLY).hasRole(ROLE_ADMIN)
    }

    private fun configureUrisOpenForAll(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(*URIS_ALLOWED).permitAll()
    }
}
