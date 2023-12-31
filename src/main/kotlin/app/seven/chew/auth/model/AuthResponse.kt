package app.seven.chew.auth.model

data class AuthResponse (
    val user: User,
    val session: Session
)

data class Session (
    val accessToken: String,
    val refreshToken: String
)