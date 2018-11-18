package mc.oauth2.app

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Mikhail Chalabine
 * @since
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(McOAuth2::class))
class McOAuth2UnitTest {

    @Test
    fun testRunning() {
    }
}
