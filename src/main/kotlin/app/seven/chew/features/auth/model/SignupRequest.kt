package app.seven.chew.features.auth.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class SignupRequest(
    @NotBlank
    val name: String,

    @Past
    val dob: LocalDate,

    @Min(9, message = "Invalid phone number")
    @Max(13, message = "Invalid phone number")
    val phone: String,

    @Email(message = "Invalid email")
    val email: String,

    @NotBlank
    val password: String,
);