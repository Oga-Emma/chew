package app.seven.chew.user.service

import app.seven.chew.user.exception.NotFoundException
import app.seven.chew.user.entity.UserProfile
import app.seven.chew.user.repository.UserProfileRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userProfileRepository: UserProfileRepository,
) {
    fun getUser(id: UUID): UserProfile {
        return userProfileRepository.findById(id).orElseThrow {
            NotFoundException("User with id not found")
        }
    }

    fun updateUser(userProfile: UserProfile) {
        userProfileRepository.save(userProfile)
    }
}