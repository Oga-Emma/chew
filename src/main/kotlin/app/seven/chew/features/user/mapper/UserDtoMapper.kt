package app.seven.chew.features.user.mapper

import app.seven.chew.features.user.model.User
import app.seven.chew.features.user.model.UserDto
import org.springframework.stereotype.Component

@Component
class UserDtoMapper {
    fun from(user: User): UserDto {
        return UserDto(
            id = user.id!!,
            name = user.name,
            dob = user.dob,
            phone = user.phone,
            email = user.email,
        )
    }
}