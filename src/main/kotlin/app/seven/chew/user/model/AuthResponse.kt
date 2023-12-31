package app.seven.chew.user.model

import app.seven.chew.user.entity.UserProfile

data class AuthResponse (
    val userProfile: UserProfile,
    val session: Session
)

data class Session (
    val accessToken: String,
    val refreshToken: String
)