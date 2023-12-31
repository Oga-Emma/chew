package app.seven.chew

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestChewApplication::class])
@ActiveProfiles("test")
abstract class BaseIntegrationTest {


}