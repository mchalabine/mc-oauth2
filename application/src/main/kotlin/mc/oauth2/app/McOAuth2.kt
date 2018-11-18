package mc.oauth2.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class McOAuth2() {

    fun main(args: Array<String>) {
        SpringApplicationBuilder(McOAuth2::class.java).run(*args)
    }
}

