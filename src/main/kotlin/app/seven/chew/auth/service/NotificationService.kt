package app.seven.chew.auth.service

import app.seven.chew.auth.model.entity.User
import org.springframework.stereotype.Service

@Service
class NotificationService {
    fun newSignup(user: User) {
        TODO("Not yet implemented")
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