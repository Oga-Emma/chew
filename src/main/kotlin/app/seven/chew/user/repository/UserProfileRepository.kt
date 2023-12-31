package app.seven.chew.user.repository

import app.seven.chew.user.entity.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserProfileRepository: JpaRepository<UserProfile, UUID> {
    fun findUserByEmail(email: String): UserProfile?
}