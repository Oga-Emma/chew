package app.seven.chew.features.auth.business

import app.seven.chew.features.auth.mapper.AuthResponseMapper
import app.seven.chew.features.auth.mapper.AuthUserMapper
import app.seven.chew.features.auth.model.AuthResponse
import app.seven.chew.features.auth.model.AuthUser
import app.seven.chew.features.auth.model.LoginRequest
import app.seven.chew.features.auth.model.SignupRequest
import app.seven.chew.features.auth.service.AuthService
import app.seven.chew.exception.EmailInUseException
import app.seven.chew.exception.InvalidCredentialException
import org.springframework.stereotype.Component

@Component
class AuthBusiness(
    val authService: AuthService,
    val authResponseMapper: AuthResponseMapper,
    val authUserMapper: AuthUserMapper
) {
    fun login(request: LoginRequest): AuthResponse {
        val authUser = authService.getUserWithEmail(request.email)
            ?: throw InvalidCredentialException()

        authService.validateForLogin(authUser, request.password)
        return this.createSession(authUser)
    }

    fun signup(request: SignupRequest): AuthResponse {
        authService.getUserWithEmail(request.email).let {
            if (it != null) {
                throw EmailInUseException()
            }
        }

        val role = "USER"
        val encodedPassword = authService.encryptPassword(request.password)
        val createUserData = authUserMapper.from(request, encodedPassword = encodedPassword, role)
        val authUser = authService.createAccount(createUserData)

        return this.createSession(authUser)
    }

    private fun createSession(authUser: AuthUser): AuthResponse {
        val session = authService.createSession(authUser)
        return authResponseMapper.from(authUser, session)
    }
}