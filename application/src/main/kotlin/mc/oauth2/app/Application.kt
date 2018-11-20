package mc.oauth2.app

import mc.oauth2.config.AppConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(AppConfiguration::class)
class Application() {

    fun main(args: Array<String>) {
        SpringApplicationBuilder(Application::class.java).run(*args)
    }
}

