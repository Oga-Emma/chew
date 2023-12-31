package app.seven.chew.auth.repository

import app.seven.chew.auth.model.entity.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthUserRepository: JpaRepository<AuthUser, UUID> {
    fun findByUser_Email(user_email: String): AuthUser?
    fun findByIdAndRefreshToken(id: UUID, refreshToken: String): AuthUser?
}