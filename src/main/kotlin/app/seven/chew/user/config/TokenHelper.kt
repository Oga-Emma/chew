package app.seven.chew.user.config

import app.seven.chew.user.entity.UserProfile
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
    fun createToken(userProfile: UserProfile, ttl: Long, unit: ChronoUnit): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(ttl, ChronoUnit.DAYS))
            .subject(userProfile.name)
            .claim("userId", userProfile.id)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String, resolveUserProfile: (userId: UUID) -> UserProfile?): UserProfile? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val userId = UUID.fromString(jwt.claims["userId"] as String)
            resolveUserProfile(userId)
        } catch (e: Exception) {
            null
        }
    }
}