package app.seven.chew.features.auth.mapper

import app.seven.chew.features.auth.model.AuthUser
import app.seven.chew.features.auth.model.SignupRequest
import app.seven.chew.features.user.model.User
import org.springframework.stereotype.Component

@Component
class AuthUserMapper {
    fun from(request: SignupRequest, encodedPassword: String, role: String): AuthUser {
        return AuthUser(
            password = encodedPassword,
            user = User(
                name = request.name,
                dob = request.dob,
                phone = request.phone,
                email = request.email,
                role = role
            )
        )
    }
}