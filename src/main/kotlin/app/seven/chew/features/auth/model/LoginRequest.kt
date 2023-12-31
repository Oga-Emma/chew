package app.seven.chew.features.auth.model

import jakarta.validation.constraints.NotNull

data class LoginRequest(@NotNull val email: String, @NotNull val password: String);