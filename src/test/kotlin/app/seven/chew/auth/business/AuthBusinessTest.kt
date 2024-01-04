package app.seven.chew.auth.business

import app.seven.chew.auth.exception.EmailInUseException
import app.seven.chew.auth.mapper.AuthResponseMapper
import app.seven.chew.auth.mapper.AuthUserMapper
import app.seven.chew.auth.model.dto.AuthResponse
import app.seven.chew.auth.model.dto.SignupRequest
import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.service.AuthService
import app.seven.chew.auth.service.EventPublisherService
import app.seven.chew.auth.service.NotificationService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AuthBusinessTest {

    private val authService: AuthService = mockk()
    private val notificationService: NotificationService = mockk()
    private val authResponseMapper: AuthResponseMapper = mockk()
    private val authUserMapper: AuthUserMapper = mockk()
    private val eventPublisherService: EventPublisherService = mockk()

    private var authBusiness: AuthBusiness =
        AuthBusiness(
            authService,
            notificationService,
            authResponseMapper,
            authUserMapper,
            eventPublisherService
        )

    @Nested
    inner class CreateAccountTest {
        @Test
        fun `CreateAccountTest - when email already exist, it should fail with EmailInUseException`() {
            //given
            val email = "johndoe@mail.com"
            val request = SignupRequest(
                name = "John Doe",
                dob = LocalDate.now(),
                phone = "0701288845",
                email = email,
                password = "12345"
            )

            val user: AuthUser = mockk()
            every { authService.getUserWithEmail(email) } returns user
            every { authService.encryptPassword(any()) } returns "password"
            every { authUserMapper.from(any(), any(), any()) } returns user
            every { authService.createAccount(any()) } returns user
            every { authService.createSession(any()) } returns mockk()
            justRun { eventPublisherService.publishUserCreatedEvent(any()) }
            justRun { notificationService.newSignup(any()) }
            every { authResponseMapper.from(any(), any()) } returns mockk()

            // when
            val actual = shouldThrow<EmailInUseException> { authBusiness.createAccount(request) }

            //then
            verify(exactly = 1) { authService.getUserWithEmail(email) }
            actual.message!!.shouldBeEqual("Email is already in use")
//            verify { authService.encryptPassword(any()) wasNot called }
////            verify {
////                listOf(
////                    authService.encryptPassword(any()),
////                    authUserMapper.from(any(), any(), any()),
////                    authService.createAccount(any()),
////                    authService.createSession(any()),
////                    notificationService.newSignup(any()),
////                    authResponseMapper.from(any(), any())
////                ) wasNot Called
////            }
        }

        @Test
        fun `CreateAccountTest - when email does not exist, it should create new account`() {
            //given
            val email = "johndoe@mail.com"
            val request = SignupRequest(
                name = "John Doe",
                dob = LocalDate.now(),
                phone = "0701288845",
                email = email,
                password = "12345"
            )

            val expected = mockk<AuthResponse>()
            val user: AuthUser = mockk()
            every { authService.getUserWithEmail(email) } returns null
            every { authService.encryptPassword(any()) } returns "password"
            every { authUserMapper.from(any(), any(), any()) } returns user
            every { authService.createAccount(any()) } returns user
            every { authService.createSession(any()) } returns mockk()
            justRun { eventPublisherService.publishUserCreatedEvent(any()) }
            justRun { notificationService.newSignup(any()) }
            every { authResponseMapper.from(any(), any()) } returns expected

            // when
            val actual = authBusiness.createAccount(request)

            //then
            actual.shouldBeEqual(expected)
            verifyOrder {
                authService.getUserWithEmail(any())
                authService.encryptPassword(any())
                authUserMapper.from(any(), any(), any())
                authService.createAccount(any())
                authService.createSession(any())
                notificationService.newSignup(any())
                authResponseMapper.from(any(), any())
            }
        }
    }

//    @Nested
//    inner class LoginTest {
//        @Test
//        fun `given correct credential when login, it should return expected result`() {
//            //given
//            val request = LoginRequest("email@mail.com", "pass")
//            val user: AuthUser = mockk()
//            val session: Session = mockk()
//            val authResponse: AuthResponse = mockk()
//
//            every { authService.getUserWithEmail(request.email) } returns user
//            justRun { authService.passwordMatch(user, request.password) }
//            every { authService.createSession(user) } returns session
//            every { authResponseMapper.from(user, session) } returns authResponse
//
//            // when
//            val actual = authBusiness.login(request)
//
//            //then
//            actual.shouldBe(authResponse)
//        }
//
//        @Test
//        fun `it should get user using correct credential`() {
//            //given
//            val request = LoginRequest("email@mail.com", "pass")
//            val user: AuthUser = mockk()
//            val session: Session = mockk()
//            val authResponse: AuthResponse = mockk()
//
//            every { authService.getUserWithEmail(request.email) } returns user
//            justRun { authService.passwordMatch(user, request.password) }
//            every { authService.createSession(user) } returns session
//            every { authResponseMapper.from(user, session) } returns authResponse
//
//            // when
//            val actual = authBusiness.login(request)
//
//            //then
//            verify(exactly = 1) { authService.getUserWithEmail(request.email) }
//            actual.shouldBe(authResponse)
//        }
//
//        @Test
//        fun `when user not exist, it should throw expected exception`() {
//            //given
//            val request = LoginRequest("email@mail.com", "pass")
//            every { authService.getUserWithEmail(request.email) } returns null
//
//            // when
//            val exception = shouldThrow<InvalidCredentialException> { authBusiness.login(request) }
//
//            //then
//            verify(exactly = 1) { authService.getUserWithEmail(request.email) }
//            exception.message.shouldBe("Invalid credential")
//        }
//
//        @Test
//        fun `it should validate login credential`() {
//            //given
//            val request = LoginRequest("email@mail.com", "pass")
//            val user: AuthUser = mockk()
//            val session: Session = mockk()
//            val authResponse: AuthResponse = mockk()
//
//            every { authService.getUserWithEmail(request.email) } returns user
//            justRun { authService.passwordMatch(user, request.password) }
//            every { authService.createSession(user) } returns session
//            every { authResponseMapper.from(user, session) } returns authResponse
//
//            // when
//            val actual = authBusiness.login(request)
//
//            //then
//            verify(exactly = 1) { authService.passwordMatch(user, request.password) }
//            actual.shouldBe(authResponse)
//        }
//
//        @Test
//        fun `if validate fails, it should throw expected exception`() {
//            //given
//            val request = LoginRequest("email@mail.com", "pass")
//            val user: AuthUser = mockk()
//
//            every { authService.getUserWithEmail(request.email) } returns user
//            every { authService.passwordMatch(user, request.password) } throws InvalidCredentialException()
//
//            // when
//            val exception = shouldThrow<InvalidCredentialException> { authBusiness.login(request) }
//
//            //then
//            verify(exactly = 1) { authService.passwordMatch(user, request.password) }
//            exception.message.shouldBe("Invalid credential")
//        }
//
//        @Test
//        fun `it should create session as expected`() {
//            //given
//            val request = LoginRequest("email@mail.com", "pass")
//            val user: AuthUser = mockk()
//            val session: Session = mockk()
//            val authResponse: AuthResponse = mockk()
//
//            every { authService.getUserWithEmail(request.email) } returns user
//            justRun { authService.passwordMatch(user, request.password) }
//            every { authService.createSession(user) } returns session
//            every { authResponseMapper.from(user, session) } returns authResponse
//
//            // when
//            val actual = authBusiness.login(request)
//
//            //then
//            verify(exactly = 1) { authService.createSession(user) }
//            actual.shouldBe(authResponse)
//        }
//    }
}