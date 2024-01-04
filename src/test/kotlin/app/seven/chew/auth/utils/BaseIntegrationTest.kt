package app.seven.chew.auth.utils

import app.seven.chew.auth.TestChewApplication
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner



@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestChewApplication::class])
abstract class BaseIntegrationTest {

}
