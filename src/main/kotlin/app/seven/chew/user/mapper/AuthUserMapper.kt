package app.seven.chew.user.mapper

import app.seven.chew.user.model.AuthUser
import app.seven.chew.user.model.SignupRequest
import app.seven.chew.user.entity.UserProfile
import org.springframework.stereotype.Component

@Component
class AuthUserMapper {
    fun from(request: SignupRequest, encodedPassword: String, role: String): AuthUser {
        return AuthUser(
            password = encodedPassword,
            userProfile = UserProfile(
                name = request.name,
                dob = request.dob,
                phone = request.phone,
                email = request.email,
                role = role
            )
        )
    }
}