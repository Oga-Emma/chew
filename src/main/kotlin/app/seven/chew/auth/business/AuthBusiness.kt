package app.seven.chew.auth.business

import app.seven.chew.auth.mapper.AuthResponseMapper
import app.seven.chew.auth.mapper.AuthUserMapper
import app.seven.chew.auth.model.AuthResponse
import app.seven.chew.auth.model.AuthUser
import app.seven.chew.auth.model.LoginRequest
import app.seven.chew.auth.model.SignupRequest
import app.seven.chew.auth.service.AuthService
import app.seven.chew.auth.exception.EmailInUseException
import app.seven.chew.auth.exception.InvalidCredentialException
import app.seven.chew.auth.rabbitmq.AccountCreatedEventRabbitMqPublisher
import app.seven.chew.auth.rabbitmq.model.ProfileUpdatedEvent
import org.springframework.stereotype.Component

@Component
class AuthBusiness(
    val authService: AuthService,
    val authResponseMapper: AuthResponseMapper,
    var accountCreatedEventRabbitMqPublisher: AccountCreatedEventRabbitMqPublisher,
    val authUserMapper: AuthUserMapper
) {
    fun login(request: LoginRequest): AuthResponse {
        val authUser = authService.getUserWithEmail(request.email)
            ?: throw InvalidCredentialException()

//        authService.validateForLogin(authUser, request.password)
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

        accountCreatedEventRabbitMqPublisher.publishUserModifiedEvent(
            ProfileUpdatedEvent(
                id = authUser.id!!,
                name = authUser.user.name,
                dob = authUser.user.dob,
                phone = authUser.user.phone,
                email = authUser.user.email,
                role = authUser.user.role,
            )
        )
        return this.createSession(authUser)
    }

    private fun createSession(authUser: AuthUser): AuthResponse {
        val session = authService.createSession(authUser)
        return authResponseMapper.from(authUser, session)
    }
}