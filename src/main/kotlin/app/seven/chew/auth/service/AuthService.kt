package app.seven.chew.auth.service

import app.seven.chew.auth.model.AuthUser
import app.seven.chew.auth.model.Session
import app.seven.chew.auth.repository.AuthUserRepository
import app.seven.chew.auth.config.TokenHelper
import app.seven.chew.auth.exception.InvalidCredentialException
import app.seven.chew.auth.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import kotlin.jvm.optionals.getOrNull

@Service
class AuthService(
    val authUserRepository: AuthUserRepository,
    val tokenHelper: TokenHelper,
    val passwordEncoder: PasswordEncoder
) {
    fun createAccount(authUser: AuthUser): AuthUser {
        return authUserRepository.save(authUser)
    }

    fun getUserWithEmail(email: String): AuthUser? {
        return authUserRepository.findByUser_Email(email)
    }

    fun getUserFromToken(token: String?): User? {
        if (token.isNullOrBlank()) {
            return null
        }

        return tokenHelper.parseToken(token) { userId ->
            authUserRepository.findById(userId).getOrNull()?.user
        }
    }

    fun createSession(authUser: AuthUser): Session {
        val session = Session(
            accessToken = this.createAccessToken(authUser),
            refreshToken = this.createRefreshToken(authUser)
        )

        this.save(authUser.copy(refreshToken = session.refreshToken))
        return session
    }

    fun encryptPassword(password: String): String {
       return passwordEncoder.encode(password)
    }

    fun validateForLogin(authUser: AuthUser, password: String) {
        if (!passwordEncoder.matches(password, authUser.password)) {
            throw InvalidCredentialException()
        }
    }

    private fun save(authUser: AuthUser): AuthUser {
        return authUserRepository.save(authUser)
    }

    private fun createAccessToken(authUser: AuthUser): String {
        return tokenHelper.createToken(authUser.user, 30L, ChronoUnit.MINUTES)
    }

    private fun createRefreshToken(authUser: AuthUser): String {
        return tokenHelper.createToken(authUser.user, 30L, ChronoUnit.DAYS)
    }
}