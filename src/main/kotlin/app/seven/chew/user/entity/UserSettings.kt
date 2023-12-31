package app.seven.chew.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Column
import java.util.UUID

@Entity
@Table(name = "user_settings")
data class UserSettings (
    @Id
    var userId: UUID? = null,

    @Column(nullable = false)
    var emailNotification: Boolean,

    @Column(nullable = false)
    var pushNotification: Boolean,

    @Column(nullable = false)
    var smsNotification: Boolean,

    @Column(nullable = false)
    var language: String
)