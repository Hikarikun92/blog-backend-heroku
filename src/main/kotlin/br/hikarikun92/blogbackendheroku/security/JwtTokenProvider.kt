package br.hikarikun92.blogbackendheroku.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtTokenProvider(
    @Value("\${app.jwt-secret}") jwtSecret: String,
    @param:Value("\${app.jwt-expiration-ms}") private val jwtExpirationInMs: Long
) {
    private val jwtSecret: Key
    private val parser: JwtParser

    init {
        this.jwtSecret = Keys.hmacShaKeyFor(jwtSecret.encodeToByteArray())
        this.parser = Jwts.parserBuilder()
            .setSigningKey(this.jwtSecret)
            .build()
    }

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
            .signWith(jwtSecret, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUsernameFromJwt(token: String): String {
        return parseToken(token)
            .body
            .subject
    }

    private fun parseToken(token: String): Jws<Claims> {
        return parser.parseClaimsJws(token)
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