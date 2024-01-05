package app.seven.chew.user

import app.seven.chew.user.model.AuthResponse
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class AuthControllerTest {
    private val authBusiness: AuthBusiness = mockk()
    private val authController: AuthController = AuthController(authBusiness)

    @Nested
    inner class LoginTest {
        @Test
        fun `Given valid parameters, when login, it should perform login`() {
            // given
            val request = mockk<LoginRequest>()
            val response = mockk<AuthResponse>()

            every { authBusiness.login(request) } returns response

            //when
            val result = authController.login(request)

            //then
            result.shouldBe(ResponseEntity.ok(response))
        }
    }


//    @Test
//    fun testSignup() {
//        1.shouldBeEqual(1)
//    }
//
//    @Test
//    fun testForgotPassword() {
//        1.shouldBeEqual(1)
//    }
//
//    @Test
//    fun testChangePassword() {
//        1.shouldBeEqual(1)
//
//    }
}