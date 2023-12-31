package app.seven.chew.auth.service

import app.seven.chew.auth.exception.NotFoundException
import app.seven.chew.auth.model.User
import app.seven.chew.auth.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
) {
    fun getUser(id: UUID): User {
        return userRepository.findById(id).orElseThrow {
            NotFoundException("User with id not found")
        }
    }

    fun updateUser(user: User) {
        userRepository.save(user)
    }
}