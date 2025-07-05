package com.mercadolivro.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generateToken(id: Int) : String {
        val key = Keys.hmacShaKeyFor(secret!!.toByteArray(Charsets.UTF_8))

        return Jwts.builder()
            .subject(id.toString())
            .expiration(Date(System.currentTimeMillis().plus(expiration!!)))
            .signWith(key)
            .compact();
    }
}