package com.teamsparta.exhibitionnewsfeed.domain.auth

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

class JwtTokenProvider {
    companion object {
        const val SECRET_KEY = "IguOg/KpN3c0VVRkfjxnlDXXkLx/cXUxzuGM5UKAevI="
    }

    private val key: SecretKey = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(user: User): String {
        return generateToken(user, "accessToken", Duration.ofMinutes(10))
    }

    fun generateRefreshToken(user: User): String {
        return generateToken(user, "refreshToken", Duration.ofDays(1))
    }

    private fun generateToken(user: User, subject: String, duration: Duration): String {
        val claims = Jwts.claims().add("userId", user.id).build()
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer("team6.explorers")
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(duration)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        } catch (e: MalformedJwtException) {
            throw IllegalArgumentException("Invalid Token")
        } catch (e: SignatureException) {
            throw IllegalArgumentException("Invalid Token")
        } catch (e: ExpiredJwtException) {
            throw IllegalArgumentException("Expired Token.")
        }
        return true
    }

    fun getSubject(token: String): String = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .payload
        .subject

    fun getUserId(token: String): Long {
        val payload = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
        return payload["userId"].toString().toLong()
    }
}