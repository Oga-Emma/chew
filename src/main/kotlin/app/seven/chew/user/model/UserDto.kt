package app.seven.chew.user.model

import java.time.LocalDate
import java.util.UUID

data class UserDto (
    val id: UUID,
    val name: String,
    val dob: LocalDate,
    val phone: String,
    val email: String
)

