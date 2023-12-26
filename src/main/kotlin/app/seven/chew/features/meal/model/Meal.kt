package app.seven.chew.features.meal.model

import app.seven.chew.features.user.model.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "meals")
data class Meal (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = true)
    var refreshToken: String? = null,

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    var user: User
)