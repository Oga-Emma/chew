package app.seven.chew.auth.model.dto

import app.seven.chew.auth.model.entity.User

data class AuthResponse (
    val user: User,
    val session: Session
)

data class Session (
    val accessToken: String,
    val refreshToken: String
)