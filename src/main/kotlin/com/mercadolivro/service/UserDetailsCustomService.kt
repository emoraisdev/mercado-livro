package com.mercadolivro.service

import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {
    override fun loadUserByUsername(customerId: String): UserDetails {

        val customer = customerRepository.findById(customerId.toInt())
            .orElseThrow { AuthenticationException("Usuário Não Encontrado", "999") }

        return UserCustomDetails(customer)

    }
}