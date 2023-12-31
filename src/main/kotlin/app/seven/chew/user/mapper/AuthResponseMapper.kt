package app.seven.chew.user.mapper

import app.seven.chew.user.model.AuthResponse
import app.seven.chew.user.model.AuthUser
import app.seven.chew.user.model.Session
import org.springframework.stereotype.Component

@Component
class AuthResponseMapper {
    fun from(authUser: AuthUser, session: Session): AuthResponse {
        return AuthResponse(
            userProfile = authUser.userProfile,
            session = session
        )
    }
}