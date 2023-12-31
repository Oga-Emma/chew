package app.seven.chew.auth.repository

import app.seven.chew.auth.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, UUID> {
    fun findUserByEmail(email: String): User?
}