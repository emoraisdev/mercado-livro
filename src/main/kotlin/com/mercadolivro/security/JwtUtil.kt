package com.mercadolivro.security

import com.mercadolivro.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generateToken(id: Int) : String {

        return Jwts.builder()
            .subject(id.toString())
            .expiration(Date(System.currentTimeMillis().plus(expiration!!)))
            .signWith(getKey())
            .compact();
    }

    private fun getKey(): SecretKey? {
        return Keys.hmacShaKeyFor(secret!!.toByteArray(Charsets.UTF_8))
    }

    fun isTokenValid(token: String): Boolean {

        val claims = getClaims(token)

        if (claims.subject == null || claims.expiration == null
            || Date().after(claims.expiration)) {

            return false
        }

        return true
    }

    private fun getClaims(token: String): Claims {

        try {
            return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).payload
        } catch (ex: Exception) {
            throw AuthenticationException("Token Inválido.", "999")
        }
    }

    fun getSubject(token: String) : String {
        return getClaims(token).subject
    }
}