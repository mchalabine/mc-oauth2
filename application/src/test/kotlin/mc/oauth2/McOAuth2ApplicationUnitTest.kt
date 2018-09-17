package mc.oauth2

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Mikhail Chalabine
 * @since
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(McOAuth2Application::class))
class McOAuth2ApplicationUnitTest {

    @Test
    fun testRunning() {
    }
}
