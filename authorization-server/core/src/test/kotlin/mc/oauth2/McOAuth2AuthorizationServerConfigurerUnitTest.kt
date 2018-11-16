package mc.oauth2

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(McOAuth2AuthorizationServerConfigurer::class)])
class McOAuth2AuthorizationServerConfigurerUnitTestMcOAuth2AuthorizationServerConfigurerUnitTest {

    @Autowired
    lateinit var context: ApplicationContext


    @Test
    fun test() {
        assert(true)
    }
}