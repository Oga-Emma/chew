package app.seven.chew.auth.mapper

import app.seven.chew.auth.model.entity.User
import app.seven.chew.auth.model.dto.UserDto
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