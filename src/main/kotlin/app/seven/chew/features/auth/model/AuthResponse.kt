package app.seven.chew.features.auth.model

import app.seven.chew.features.user.model.User

data class AuthResponse (
    val user: User,
    val session: Session
)

data class Session (
    val accessToken: String,
    val refreshToken: String
)