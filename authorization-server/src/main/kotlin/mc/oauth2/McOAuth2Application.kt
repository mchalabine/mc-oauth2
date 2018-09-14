package mc.oauth2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter

@SpringBootApplication
class McOAuth2Application: AuthorizationServerConfigurerAdapter() {

    fun main(args: Array<String>) {
        SpringApplicationBuilder(McOAuth2Application::class.java).run(*args)
    }
}

