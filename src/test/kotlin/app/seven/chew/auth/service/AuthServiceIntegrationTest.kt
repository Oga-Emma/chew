package app.seven.chew.auth.service

import app.seven.chew.auth.config.TokenHelper
import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.model.entity.User
import app.seven.chew.auth.repository.AuthUserRepository
import app.seven.chew.auth.utils.BaseIntegrationTest
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.jvm.optionals.getOrNull

class AuthServiceIntegrationTest(
    @Autowired val authUserRepository: AuthUserRepository,
    @Autowired val authService: AuthService,
    @Autowired val tokenHelper: TokenHelper
) : BaseIntegrationTest() {

    @BeforeEach
    fun setUp() {

        authUserRepository.deleteAll()
    }

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
                email = "email4@mail.com", phone = "1234567",
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
        val expected = authService.getUserWithEmail(ourUser.user.email)

        //then
        expected.shouldNotBeNull()
        expected.shouldBeEqualToIgnoringFields(ourUser, AuthUser::id, AuthUser::user)
        expected.user.shouldBeEqualToIgnoringFields(ourUser.user, User::id, User::role, User::authUser)
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
        val expected = authService.getUserWithEmail("unknown@email.com")

        //then
        expected.shouldBeNull()
    }

    @Test
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
        val expected: AuthUser? = authService.getUserFromToken(token)

        //then
        expected.shouldNotBeNull()
        expected.user.shouldBeEqualToIgnoringFields(savedUser.user, User::role, User::authUser)
    }

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
