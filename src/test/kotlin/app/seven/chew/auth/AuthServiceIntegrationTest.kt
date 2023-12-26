package app.seven.chew.auth

import app.seven.chew.BaseIntegrationText
import app.seven.chew.features.auth.model.AuthUser
import app.seven.chew.features.auth.repository.AuthUserRepository
import app.seven.chew.features.auth.service.AuthService
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

class AuthServiceIntegrationTest(
    @Autowired val authUserRepository: AuthUserRepository,
    @Autowired val authService: AuthService
): BaseIntegrationText() {

    @Test
    fun createAccount() {
        //given
        val authUser = AuthUser(
            password = "1234",
            user = User(
                email = "email@mail.com",
                phone = "1234567",
                name = "Seven apps",
                dob = LocalDate.now(),
                role = "user"
            )
        )

        //when
        val actual = authService.createAccount(authUser)
        val dbuser = authUserRepository.findById(actual.id!!).getOrNull()

        //then
        dbuser.shouldNotBeNull()
        dbuser.user.email.shouldBeEqual(authUser.user.email)
        dbuser.id!!.shouldBeEqual(dbuser.user.id!!)
    }
//
//    @Test
//    fun getUserWithEmail() {
//    }
//
//    @Test
//    fun getUserFromToken() {
//    }
//
//    @Test
//    fun createSession() {
//    }
//
//    @Test
//    fun encryptPassword() {
//    }
//
//    @Test
//    fun validateForLogin() {
//    }
//
//    @Test
//    fun getAuthUserRepository() {
//    }
//
//    @Test
//    fun getTokenHelper() {
//    }
//
//    @Test
//    fun getPasswordEncoder() {
//    }
}