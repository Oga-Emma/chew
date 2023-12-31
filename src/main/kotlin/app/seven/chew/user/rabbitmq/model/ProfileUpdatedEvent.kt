package app.seven.chew.user.rabbitmq.model

import java.time.LocalDate
import java.util.UUID

data class ProfileUpdatedEvent (
    val id: UUID,
    val name: String,
    val dob: LocalDate,
    val phone: String,
    val email: String,
    val role: String,
)