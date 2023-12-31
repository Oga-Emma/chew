package app.seven.chew.auth.model.dto

data class CompletePasswordResetRequest (
    val email: String,
    val passwordResetCode: String,
    val newPassword: String
)

