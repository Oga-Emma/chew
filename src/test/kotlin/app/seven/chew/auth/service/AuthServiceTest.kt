package app.seven.chew.auth.service

import app.seven.chew.auth.config.TokenHelper
import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.repository.AuthUserRepository
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceTest {

    private val authUserRepository: AuthUserRepository = mockk()
    private val tokenHelper: TokenHelper = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val authService = AuthService(authUserRepository, tokenHelper, passwordEncoder)

    @Test
    fun `testGetUserWithEmail - it should get user with email and return as expected`() {
        //give
        val email = "email@mail.com"
        val expected: AuthUser = mockk()

        every { authUserRepository.findByUserEmail(email) }.returns(expected)

        //when
        val actual = authService.getUserWithEmail(email)

        //then
        actual?.shouldBeEqual(expected)
    }
}