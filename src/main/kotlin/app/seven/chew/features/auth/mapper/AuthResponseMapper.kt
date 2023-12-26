package app.seven.chew.features.auth.mapper

import app.seven.chew.features.auth.model.AuthResponse
import app.seven.chew.features.auth.model.AuthUser
import app.seven.chew.features.auth.model.Session
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