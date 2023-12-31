package app.seven.chew.auth.config

import app.seven.chew.auth.model.entity.AuthUser
import app.seven.chew.auth.model.entity.User
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Component
class TokenHelper (
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
) {
    fun createToken(user: User, ttl: Long, unit: ChronoUnit): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(ttl, ChronoUnit.DAYS))
            .subject(user.id.toString())
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String, resolveUser: (userId: UUID) -> AuthUser?): AuthUser? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val userId = UUID.fromString(jwt.subject as String)
            resolveUser(userId)
        } catch (e: Exception) {
            null
        }
    }
}