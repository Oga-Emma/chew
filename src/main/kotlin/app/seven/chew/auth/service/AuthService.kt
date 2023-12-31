package app.seven.chew.auth.service

import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.model.dto.Session
import app.seven.chew.auth.repository.AuthUserRepository
import app.seven.chew.auth.config.TokenHelper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import java.util.*
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

    fun getUserById(userId: UUID): AuthUser? {
        return authUserRepository.findById(userId).getOrNull()
    }

    fun getUserFromToken(token: String?): AuthUser? {
        if (token.isNullOrBlank()) {
            return null
        }

        return tokenHelper.parseToken(token) { userId ->
            authUserRepository.findById(userId).getOrNull()
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

    fun passwordMatch(authUser: AuthUser, password: String): Boolean {
        return passwordEncoder.matches(password, authUser.password)
    }

    fun getUserByRefreshToken(userId: UUID, refreshToken: String): AuthUser? {
        return authUserRepository.findByIdAndRefreshToken(userId, refreshToken)
    }

    fun save(authUser: AuthUser): AuthUser {
        return authUserRepository.save(authUser)
    }

    private fun createAccessToken(authUser: AuthUser): String {
        return tokenHelper.createToken(authUser.user, 30L, ChronoUnit.MINUTES)
    }

    private fun createRefreshToken(authUser: AuthUser): String {
        return tokenHelper.createToken(authUser.user, 30L, ChronoUnit.DAYS)
    }
}