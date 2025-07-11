package com.mercadolivro.security

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.Customer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(
    private val customer: Customer
) : UserDetails {

    val id = customer.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        customer.roles.map { SimpleGrantedAuthority(it.description) }.toMutableSet()

    override fun getPassword(): String = customer.password

    override fun getUsername(): String = customer.id.toString()

    override fun isEnabled(): Boolean = customer.status == CustomerStatus.ATIVO
}