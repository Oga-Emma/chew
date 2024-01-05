package app.seven.chew.user.service

import app.seven.chew.user.exception.NotFoundException
import app.seven.chew.user.model.entity.UserProfile
import app.seven.chew.user.repository.UserProfileRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    val userProfileRepository: UserProfileRepository,
) {
    fun getUser(id: UUID): UserProfile {
        return userProfileRepository.findById(id).orElseThrow {
            NotFoundException("User with id not found")
        }
    }
    fun findUser(id: UUID): UserProfile? {
        return userProfileRepository.findById(id).getOrNull()
    }

    fun crateUserProfile(userProfile: UserProfile): UserProfile {
        return userProfileRepository.save(userProfile)
    }

    fun updateUserProfile(userProfile: UserProfile): UserProfile {
        return userProfileRepository.save(userProfile)
    }
}