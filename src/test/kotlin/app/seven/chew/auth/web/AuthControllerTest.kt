package app.seven.chew.auth.web

import app.seven.chew.auth.business.AuthBusiness
import app.seven.chew.auth.model.dto.AuthResponse
import app.seven.chew.auth.model.dto.SignupRequest
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class AuthControllerTest {
    private val authBusiness: AuthBusiness = mockk()
    private val authController: AuthController = AuthController(authBusiness)

    @Test
    fun `signupTest - Given valid parameters, it should register account`() {
        // given
        val request = mockk<SignupRequest>()
        val response = mockk<AuthResponse>()

        every { authBusiness.createAccount(request) } returns response

        //when
        val result = authController.signup(request)

        //then
        verify { exactly(1); authBusiness.createAccount(request) }
        result.shouldBe(ResponseEntity.ok(response))
    }
}
