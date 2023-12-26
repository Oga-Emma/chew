package app.seven.chew.features.user.repository

import app.seven.chew.features.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, UUID> {
    fun findUserByEmail(email: String): User?
}