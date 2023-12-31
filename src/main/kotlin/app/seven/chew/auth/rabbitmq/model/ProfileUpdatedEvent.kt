package app.seven.chew.auth.rabbitmq.model

import java.time.LocalDate
import java.util.*

data class ProfileUpdatedEvent (
    val id: UUID,
    val name: String,
    val dob: LocalDate,
    val phone: String,
    val email: String,
    val role: String,
)