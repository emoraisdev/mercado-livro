package com.mercadolivro.security

import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.service.UserDetailsCustomService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userService: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val authorization = request.getHeader("Authorization")

        if (authorization != null && authorization.startsWith("Bearer ")) {
            val auth = getAuthentication(authorization.split(" ")[1])

            SecurityContextHolder.getContext().authentication = auth
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {

        if (!jwtUtil.isTokenValid(token)) {
            throw AuthenticationException("Token Inválido.", "999")
        }

        val subject = jwtUtil.getSubject(token)

        val user = userService.loadUserByUsername(subject)

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}