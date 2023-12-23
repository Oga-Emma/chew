package app.seven.chew.auth

import app.seven.chew.auth.mapper.AuthResponseMapper
import app.seven.chew.auth.mapper.AuthUserMapper
import app.seven.chew.auth.model.AuthResponse
import app.seven.chew.auth.model.AuthUser
import app.seven.chew.auth.model.LoginRequest
import app.seven.chew.auth.model.Session
import app.seven.chew.exception.InvalidCredentialException
import app.seven.chew.user.model.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AuthBusinessTest {
    private var authService: AuthService = mockk()
    private var authResponseMapper: AuthResponseMapper = mockk()
    private var authUserMapper: AuthUserMapper = mockk()
    private var authBusiness: AuthBusiness = AuthBusiness(authService, authResponseMapper, authUserMapper)

    @Nested
    inner class LoginTest {
        @Test
        fun `given correct credential when login, it should return expected result`() {
            //given
            val request = LoginRequest("email@mail.com", "pass")
            val user: AuthUser = mockk()
            val session: Session = mockk()
            val authResponse: AuthResponse = mockk()

            every { authService.getUserWithEmail(request.email) } returns user
            justRun { authService.validateForLogin(user, request.password) }
            every { authService.createSession(user) } returns session
            every { authResponseMapper.from(user, session) } returns authResponse

            // when
            val actual = authBusiness.login(request)

            //then
            actual.shouldBe(authResponse)
        }

        @Test
        fun `it should get user using correct credential`() {
            //given
            val request = LoginRequest("email@mail.com", "pass")
            val user: AuthUser = mockk()
            val session: Session = mockk()
            val authResponse: AuthResponse = mockk()

            every { authService.getUserWithEmail(request.email) } returns user
            justRun { authService.validateForLogin(user, request.password) }
            every { authService.createSession(user) } returns session
            every { authResponseMapper.from(user, session) } returns authResponse

            // when
            val actual = authBusiness.login(request)

            //then
            verify(exactly = 1) { authService.getUserWithEmail(request.email) }
            actual.shouldBe(authResponse)
        }

        @Test
        fun `when user not exist, it should throw expected exception`() {
            //given
            val request = LoginRequest("email@mail.com", "pass")
            every { authService.getUserWithEmail(request.email) } returns null

            // when
            val exception = shouldThrow<InvalidCredentialException> { authBusiness.login(request) }

            //then
            verify(exactly = 1) { authService.getUserWithEmail(request.email) }
            exception.message.shouldBe("Invalid credential")
        }

        @Test
        fun `it should validate login credential`() {
            //given
            val request = LoginRequest("email@mail.com", "pass")
            val user: AuthUser = mockk()
            val session: Session = mockk()
            val authResponse: AuthResponse = mockk()

            every { authService.getUserWithEmail(request.email) } returns user
            justRun { authService.validateForLogin(user, request.password) }
            every { authService.createSession(user) } returns session
            every { authResponseMapper.from(user, session) } returns authResponse

            // when
            val actual = authBusiness.login(request)

            //then
            verify(exactly = 1) { authService.validateForLogin(user, request.password) }
            actual.shouldBe(authResponse)
        }

        @Test
        fun `if validate fails, it should throw expected exception`() {
            //given
            val request = LoginRequest("email@mail.com", "pass")
            val user: AuthUser = mockk()

            every { authService.getUserWithEmail(request.email) } returns user
            every { authService.validateForLogin(user, request.password) } throws InvalidCredentialException()

            // when
            val exception = shouldThrow<InvalidCredentialException> { authBusiness.login(request) }

            //then
            verify(exactly = 1) { authService.validateForLogin(user, request.password) }
            exception.message.shouldBe("Invalid credential")
        }

        @Test
        fun `it should create session as expected`() {
            //given
            val request = LoginRequest("email@mail.com", "pass")
            val user: AuthUser = mockk()
            val session: Session = mockk()
            val authResponse: AuthResponse = mockk()

            every { authService.getUserWithEmail(request.email) } returns user
            justRun { authService.validateForLogin(user, request.password) }
            every { authService.createSession(user) } returns session
            every { authResponseMapper.from(user, session) } returns authResponse

            // when
            val actual = authBusiness.login(request)

            //then
            verify(exactly = 1) { authService.createSession(user) }
            actual.shouldBe(authResponse)
        }
    }
}