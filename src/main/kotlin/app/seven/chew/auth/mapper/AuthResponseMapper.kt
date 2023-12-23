package app.seven.chew.auth.mapper

import app.seven.chew.auth.model.AuthResponse
import app.seven.chew.auth.model.AuthUser
import app.seven.chew.auth.model.Session
import app.seven.chew.user.model.User
import org.springframework.stereotype.Component

@Component
class AuthResponseMapper {
    fun from(authUser: AuthUser, session: Session): AuthResponse {
        return AuthResponse(
            user = authUser.user,
            session = session
        )
    }
}