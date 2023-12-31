package app.seven.chew.user.service

import app.seven.chew.BaseIntegrationTest
import app.seven.chew.user.config.TokenHelper
import app.seven.chew.user.model.AuthUser
import app.seven.chew.user.entity.UserProfile
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
        val authUserProfile = AuthUser(
            password = "1234",
            userProfile = UserProfile(
                email = "email@mail.com",
                phone = "1234567",
                name = "Seven apps",
                dob = LocalDate.now(),
                role = "user"
            )
        )

        //when
        val actual = authService.createAccount(authUserProfile)
        val dbuser = authUserRepository.findById(actual.id!!).getOrNull()

        //then
        dbuser.shouldNotBeNull()
        dbuser.user.email.shouldBeEqual(authUserProfile.userProfile.email)
        dbuser.id!!.shouldBeEqual(dbuser.user.id!!)
    }

    @Test
    fun `getUserWithEmail - when data is present, should return appropriate data`() {
        //given
        val ourUserProfile = AuthUser(
            password = "1234", userProfile = UserProfile(
                email = "email1@mail.com", phone = "1234567",
                name = "Seven apps", dob = LocalDate.now(), role = "user"
            )
        )

        authUserRepository.saveAll(
            listOf(
                ourUserProfile,
                AuthUser(
                    password = "1234", userProfile = UserProfile(
                        email = "email2@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
            )
        )

        //when
        val authUser = authService.getUserWithEmail(ourUserProfile.userProfile.email)

        //then
        authUser.shouldNotBeNull()
        authUser.shouldBeEqualToIgnoringFields(ourUserProfile, AuthUser::id, AuthUser::userProfile)
        authUser.profile.shouldBeEqualToIgnoringFields(ourUserProfile.userProfile, UserProfile::id, UserProfile::role, UserProfile::authUser)
    }

    @Test
    fun `getUserWithEmail - when data not present, should return null`() {
        //given
        authUserRepository.saveAll(
            listOf(
                AuthUser(
                    password = "1234", userProfile = UserProfile(
                        email = "email1@mail.com", phone = "1234567",
                        name = "Seven apps", dob = LocalDate.now(), role = "user"
                    )
                ),
                AuthUser(
                    password = "1234", userProfile = UserProfile(
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