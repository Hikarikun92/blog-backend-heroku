package br.hikarikun92.blogbackendheroku.security

import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtTokenProvider(
    @param:Value("\${app.jwt-secret}") private val jwtSecret: String,
    @param:Value("\${app.jwt-expiration-ms}") private val jwtExpirationInMs: Long
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    }

    fun generateToken(username: String): String {
        val now = Date()
        val expiration = Date(now.time + jwtExpirationInMs)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUsernameFromJwt(token: String): String {
        return parseToken(token)
            .body
            .subject
    }

    private fun parseToken(token: String): Jws<Claims> {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
    }

    fun validateToken(token: String): Boolean {
        try {
            parseToken(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty", e)
        }
        return false
    }
}