package app.seven.chew.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.GenerationType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.OneToMany
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "user_profile")
data class UserProfile (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var dob: LocalDate,

    @Column(nullable = false)
    var phone: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var role: String,

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL])
    var userAddresses: List<UserAddress>? = null
)