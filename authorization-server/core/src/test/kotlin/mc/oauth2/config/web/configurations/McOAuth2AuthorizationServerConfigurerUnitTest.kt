package mc.oauth2.config.web.configurations

import mc.oauth2.config.web.configurations.McOAuth2AuthorizationServerConfigurer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [(McOAuth2AuthorizationServerConfigurer::class)])
class McOAuth2AuthorizationServerConfigurerUnitTestMcOAuth2AuthorizationServerConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext


    @Test
    fun test() {
        assert(true)
    }
}