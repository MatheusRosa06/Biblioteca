package br.pucpr.authserver.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${security.jwt.secret}") secret: String,
    @Value("\${security.jwt.expiration-minutes}") expirationMinutes: Long
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    private val expirationMillis = expirationMinutes * 60_000

    fun generateToken(email: String): String =
        Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(key)
            .compact()

    fun validateToken(token: String): String? = try {
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    } catch (_: Exception) {
        null
    }
}
