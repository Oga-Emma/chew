package app.seven.chew.auth.mapper

import app.seven.chew.auth.model.dto.AuthResponse
import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.model.dto.Session
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