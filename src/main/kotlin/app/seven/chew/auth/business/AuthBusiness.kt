package app.seven.chew.auth.business

import app.seven.chew.auth.exception.EmailInUseException
import app.seven.chew.auth.mapper.AuthResponseMapper
import app.seven.chew.auth.mapper.AuthUserMapper
import app.seven.chew.auth.model.dto.AuthResponse
import app.seven.chew.auth.model.dto.SignupRequest
import app.seven.chew.auth.service.AuthService
import app.seven.chew.auth.service.NotificationService
import org.springframework.stereotype.Component

@Component
class AuthBusiness(
    val authService: AuthService,
    val notificationService: NotificationService,
    val authResponseMapper: AuthResponseMapper,
    val authUserMapper: AuthUserMapper
) {

    fun createAccount(request: SignupRequest): AuthResponse {
        authService.getUserWithEmail(request.email)?.let {
            throw EmailInUseException()
        }

        val role = "USER"
        val encodedPassword = authService.encryptPassword(request.password)
        val createUserData = authUserMapper.from(request, encodedPassword = encodedPassword, role)
        val authUser = authService.createAccount(createUserData)

        val session = authService.createSession(authUser)
        notificationService.newSignup(authUser)

        return authResponseMapper.from(authUser, session)
    }
//
//    fun login(request: LoginRequest): AuthResponse {
//        val authUser = authService.getUserWithEmail(request.email)
//            ?: throw InvalidCredentialException()
//
//        authService.passwordMatch(authUser, request.password).also {
//            if(!it) throw InvalidCredentialException()
//        }
//
//        val session = this.createSession(authUser)
////        notificationService.newLogin(authUser.user)
//
//        return session
//    }
//
//    fun requestPasswordReset(request: ResetPasswordRequest) {
//        val authUser = authService.getUserWithEmail(request.email)
//            ?: throw InvalidCredentialException()
//
//        val passwordResetCode = this.generatePasswordResetCode(authUser)
//        notificationService.passwordResetRequested(authUser.user, passwordResetCode)
//    }
//
//    fun completePasswordReset(request: CompletePasswordResetRequest) {
//        // get password reset code
//        val authUser = authService.getUserWithEmail(request.email)
//            ?: throw InvalidCredentialException()
//
//        val passwordCodeDto = this.getPasswordResetCode(request.passwordResetCode)
//
//        // get code from cache
//        if (authUser.user.email != passwordCodeDto.email){
//            throw GenericException("Invalid request, please try again")
//        }
//
//        this.setNewPassword(authUser, request.newPassword)
//        notificationService.passwordRequestCompleted(authUser.user)
//    }
//
//    fun refreshAccessToken(userId: UUID, request: RefreshAccessTokenRequest): AuthResponse {
//        val authUser = authService.getUserByRefreshToken(userId, request.refreshToken)
//            ?: throw NotFoundException("Invalid refresh token")
//
//        return this.createSession(authUser)
//    }
//
//    fun changePassword(userId: UUID, request: ChangePasswordRequest) {
//        val authUser = authService.getUserById(userId)
//            ?: throw NotFoundException("User with id not found")
//
//        authService.passwordMatch(authUser, request.oldPassword).also {
//            if(!it) throw GenericException("Password does not match")
//        }
//
//       this.setNewPassword(authUser, request.newPassword)
//        notificationService.passwordChanged(authUser.user)
//    }
//
//    private fun setNewPassword(authUser: AuthUser, newPassword: String) {
//        val encodedPassword = authService.encryptPassword(newPassword)
//
//        authService.save(authUser.copy(password = encodedPassword))
//    }
//
//    private fun generatePasswordResetCode(authUser: AuthUser): String {
//        // generate code
//
//        // save code to cache
//        TODO("Not yet implemented")
//    }
//
//    private fun getPasswordResetCode(passwordResetCode: String): PasswordCodeDto {
//        TODO("Not yet implemented")
//    }

}
