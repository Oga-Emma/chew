package app.seven.chew.auth.service

import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.model.entity.User
import org.springframework.stereotype.Service

@Service
class NotificationService {
    fun newSignup(user: AuthUser) {
        TODO("Not yet implemented")

        /*accountCreatedEventRabbitMqPublisher.publishUserModifiedEvent(
         ProfileUpdatedEvent(
             id = authUser.id!!,
             name = authUser.user.name,
             dob = authUser.user.dob,
             phone = authUser.user.phone,
             email = authUser.user.email,
             role = authUser.user.role,
         )
     )*/
    }

    fun newLogin(user: User) {
        TODO("Not yet implemented")
    }

    fun passwordResetRequested(user: User, passwordResetCode: String) {
        TODO("Not yet implemented")
    }

    fun passwordChanged(user: User) {
        TODO("Not yet implemented")
    }

    fun passwordRequestCompleted(user: User) {
        TODO("Not yet implemented")
    }
}