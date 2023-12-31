package app.seven.chew.auth.model.dto

data class PasswordCodeDto (
    val email: String,
    val resetCode: String
)

