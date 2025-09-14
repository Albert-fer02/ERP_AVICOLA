// JwtUtil.kt - Utility for JWT token generation and validation
// Justification: Handles JWT for authentication.
// Uses JJWT library for secure token management.
// Validates tokens to ensure user authenticity.

package com.avicola.corefarm.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${jwt.expiration}") private val expiration: Long
) {

    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String? {
        return try {
            val claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).body
            claims.subject
        } catch (e: Exception) {
            null
        }
    }
}