package app.seven.chew.auth.service

import app.seven.chew.auth.exception.NotFoundException
import app.seven.chew.auth.model.dto.UserDto
import app.seven.chew.auth.model.entity.User
import app.seven.chew.auth.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    val userRepository: UserRepository,
) {
    fun getUser(id: UUID): User {
        return userRepository.findById(id).orElseThrow {
            NotFoundException("User with id not found")
        }
    }

    fun updateUser(userDto: UserDto) {
        val user = userRepository.findById(userDto.id).getOrNull()
            ?: throw NotFoundException("User with id not found")

        updateUser(
            user.copy(
                name = user.name,
                dob = user.dob,
                phone = user.phone,
            )
        )
    }

    fun updateUser(user: User) {
        userRepository.save(user)
    }
}