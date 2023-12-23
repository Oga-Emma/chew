package app.seven.chew.auth

import app.seven.chew.auth.model.AuthUser
import app.seven.chew.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthUserRepository: JpaRepository<AuthUser, UUID> {
    fun findByUser_Email(user_email: String): AuthUser?
}