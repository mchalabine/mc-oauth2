package mc.oauth2.app

import mc.oauth2.config.web.configurations.AuthorizationServerConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ApplicationConfiguration::class)
class Application {

    fun main(args: Array<String>) {
        SpringApplicationBuilder(Application::class.java).run(*args)
    }
}

