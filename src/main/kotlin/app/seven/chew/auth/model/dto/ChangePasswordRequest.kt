package app.seven.chew.auth.model.dto

data class ChangePasswordRequest (
    val oldPassword: String,
    val newPassword: String
)

