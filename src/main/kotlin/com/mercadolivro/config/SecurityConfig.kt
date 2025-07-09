package com.mercadolivro.config

import com.mercadolivro.enums.Role
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.CustomAuthenticationEntryPoint
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val userService: UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint : CustomAuthenticationEntryPoint
) {

    private val publicMatchers = arrayOf<String>()

    private val publicPostMatchers = arrayOf(
        "/customers"
    )

    private val adminMatchers = arrayOf(
        "/admin/**"
    )

    private val swaggerMatchers = arrayOf(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
    )

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors{}
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*publicMatchers).permitAll()
                    .requestMatchers(HttpMethod.POST, *publicPostMatchers).permitAll()
                    .requestMatchers(*adminMatchers).hasAuthority(
                        Role.ADMIN.description
                    ).requestMatchers(*swaggerMatchers).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .addFilter(AuthenticationFilter(authenticationManager(),
                customerRepository,
                jwtUtil))
            .addFilter(AuthorizationFilter(authenticationManager(),
                userService,
                jwtUtil))
            .exceptionHandling {
                exception -> exception.authenticationEntryPoint(customEntryPoint) }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    // Bean que define as regras CORS
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("*") // ou defina domínios específicos
//        configuration.allowedOrigins = listOf("https://seusite.com") // domínios específicos
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true // true só se você confia na origem

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}