package app.seven.chew.auth.business

import app.seven.chew.auth.rabbitmq.model.ProfileUpdatedEvent
import app.seven.chew.auth.service.UserService
import org.springframework.stereotype.Component

@Component
class UserBusiness(val userService: UserService) {
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