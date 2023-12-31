package app.seven.chew.user.mapper

import app.seven.chew.user.entity.UserProfile
import app.seven.chew.user.model.UserDto
import org.springframework.stereotype.Component

@Component
class UserDtoMapper {
    fun from(userProfile: UserProfile): UserDto {
        return UserDto(
            id = userProfile.id!!,
            name = userProfile.name,
            dob = userProfile.dob,
            phone = userProfile.phone,
            email = userProfile.email,
        )
    }
}