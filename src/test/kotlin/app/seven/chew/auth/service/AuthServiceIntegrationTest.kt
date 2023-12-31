package app.seven.chew.auth.service

import app.seven.chew.BaseIntegrationTest
import app.seven.chew.auth.config.TokenHelper
import app.seven.chew.auth.model.AuthUser
import app.seven.chew.auth.repository.AuthUserRepository
import app.seven.chew.auth.model.User
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

class AuthServiceIntegrationTest(
    @Autowired val authUserRepository: AuthUserRepository,
    @Autowired val authService: AuthService,
    @Autowired val tokenHelper: TokenHelper,
    @Autowired val passwordEncoder: PasswordEncoder
) : BaseIntegrationTest() {

    @Test
    fun `testCreateAccount - should save data to db`() {
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

    @Test
    fun `getUserWithEmail - when data is present, should return appropriate data`() {
        //given
        val ourUser = AuthUser(
            password = "1234", user = User(
                email = "email1@mail.com", phone = "1234567",
                name = "Seven apps", dob = LocalDate.now(), role = "user"
            )
        )

        authUserRepository.saveAll(
            listOf(
                ourUser,
                AuthUser(
                    password = "1234", user = User(
                        email = "email2@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
            )
        )

        //when
        val authUser = authService.getUserWithEmail(ourUser.user.email)

        //then
        authUser.shouldNotBeNull()
        authUser.shouldBeEqualToIgnoringFields(ourUser, AuthUser::id, AuthUser::user)
        authUser.user.shouldBeEqualToIgnoringFields(ourUser.user, User::id, User::role, User::authUser)
    }

    @Test
    fun `getUserWithEmail - when data not present, should return null`() {
        //given
        authUserRepository.saveAll(
            listOf(
                AuthUser(
                    password = "1234", user = User(
                        email = "email1@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
                AuthUser(
                    password = "1234", user = User(
                        email = "email2@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
            )
        )

        //when
        val authUser = authService.getUserWithEmail("unknown@email.com")

        //then
        authUser.shouldBeNull()
    }

   /* @Test
    fun `getUserFromToken - when pass`() {
        //given
        val savedUser = authUserRepository.save(
            AuthUser(
                password = "1234", user = User(
                    email = "email1@mail.com", phone = "1234567",
                    name = "Seven apps", dob = LocalDate.now(), role = "user"
                )
            )
        )

        val token = tokenHelper.createToken(savedUser.user, 10L, ChronoUnit.MINUTES)

        authUserRepository.saveAll(
            listOf(
                AuthUser(
                    password = "1234", user = User(
                        email = "email1@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
                AuthUser(
                    password = "1234", user = User(
                        email = "email2@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
            )
        )

        //when
        val user = authService.getUserFromToken(token)

        //then
        user.shouldNotBeNull()
        user.shouldBeEqualToIgnoringFields(savedUser.user, User::role, User::authUser)
    }*/
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