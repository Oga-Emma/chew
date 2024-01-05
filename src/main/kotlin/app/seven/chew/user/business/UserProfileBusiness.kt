package app.seven.chew.user.business

import app.seven.chew.auth.rabbitmq.EventPublisherService
import app.seven.chew.user.mapper.UserDtoMapper
import app.seven.chew.user.model.entity.UserProfile
import app.seven.chew.user.model.UserDto
import app.seven.chew.user.model.dto.UpdateUserProfileRequest
import app.seven.chew.user.service.UserService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserProfileBusiness(
    val userService: UserService,
    val eventPublisherService: EventPublisherService,
    val userDtoMapper: UserDtoMapper
) {
    fun updateUserProfile(userId: UUID, data: UpdateUserProfileRequest): UserProfile {
        var user = userService.getUser(userId)

        data.name?.apply {
            user = user.copy(
                name = this,
            )
        }

        data.dob?.apply {
            user = user.copy(
                dob = this,
            )
        }

        data.phone?.apply {
            user = user.copy(
                phone = this,
            )
        }

        val updatedUser = userService.updateUserProfile(user)
        eventPublisherService.publishUserUpdatedEvent(userDtoMapper.from(updatedUser))

        return updatedUser
    }

    fun handleUserCreatedEvent(event: UserDto): UserProfile {
        val user = userService.findUser(event.id)
            ?: return userService.crateUserProfile(
                UserProfile(
                    id = event.id,
                    name = event.name,
                    dob = event.dob,
                    phone = event.phone,
                    email = event.email,
                )
            )

        return userService.updateUserProfile(
            user.copy(
                name = event.name,
                dob = event.dob,
                phone = event.phone,
                email = event.email,
            )
        )
    }
}