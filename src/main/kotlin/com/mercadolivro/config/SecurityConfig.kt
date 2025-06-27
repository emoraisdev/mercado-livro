package com.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    private val publicMatchers = arrayOf<String>()

    private val publicPostMatchers = arrayOf(
        "/customer"
    )

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*publicMatchers).permitAll()
                    .requestMatchers(HttpMethod.POST, *publicPostMatchers).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { }
            .httpBasic { }

        return http.build()
    }

    @Bean
    fun bCryptPasswordEnconder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }
}