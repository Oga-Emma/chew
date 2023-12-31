package app.seven.chew.config

import app.seven.chew.BaseIntegrationTest
import app.seven.chew.auth.config.TokenHelper
import org.springframework.beans.factory.annotation.Autowired

class TokenHelperIntegrationTest(@Autowired val tokenHelper: TokenHelper) : BaseIntegrationTest() {

}