package mc.oauth2.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(*[ApplicationConfiguration::class])
class Application {

    fun main(args: Array<String>) {
        runApplication<Application>(*args)
    }
}

