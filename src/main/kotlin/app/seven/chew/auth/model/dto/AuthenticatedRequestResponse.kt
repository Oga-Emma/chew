package app.seven.chew.auth.model.dto

data class AuthenticatedRequestResponse (
    val id: String,
    val roles: List<String>,
)

