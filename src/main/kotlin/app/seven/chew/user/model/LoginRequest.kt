package app.seven.chew.user.model

import jakarta.validation.constraints.NotNull

data class LoginRequest(
    @NotNull(message = "Email is required") val email: String,
    @NotNull(message = "Password is required") val password: String
);