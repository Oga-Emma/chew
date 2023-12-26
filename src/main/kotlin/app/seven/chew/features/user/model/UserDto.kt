package app.seven.chew.features.user.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

data class UserDto (
    val id: UUID,
    val name: String,
    val dob: LocalDate,
    val phone: String,
    val email: String
)

