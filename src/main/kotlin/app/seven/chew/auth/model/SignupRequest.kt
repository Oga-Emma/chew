package app.seven.chew.auth.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class SignupRequest(
    @NotBlank(message = "Name is required")
    val name: String,

    @Past (message = "Invalid date of birth")
    val dob: LocalDate,

    @Min(9, message = "Invalid phone number")
    @Max(13, message = "Invalid phone number")
    val phone: String,

    @Email(message = "Invalid email")
    val email: String,

    @NotBlank(message = "Password is required")
    val password: String,
);