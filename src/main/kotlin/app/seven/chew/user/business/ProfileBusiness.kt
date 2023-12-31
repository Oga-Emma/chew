package app.seven.chew.user.business

import app.seven.chew.user.rabbitmq.model.ProfileUpdatedEvent
import app.seven.chew.user.service.UserService
import org.springframework.stereotype.Component

@Component
class ProfileBusiness(val userService: UserService) {
    fun updateDetails(event: ProfileUpdatedEvent) {
        val user = userService.getUser(event.id)

        userService.updateUser(
            user.copy(
                name = event.name,
                dob = event.dob,
                phone = event.phone,
                email = event.email,
            )
        )
    }
}