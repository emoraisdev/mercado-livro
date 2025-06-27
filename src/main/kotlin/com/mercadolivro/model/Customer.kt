package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import jakarta.persistence.*

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column
    var password: String,

    @Column
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "customer_roles",
        joinColumns = [JoinColumn(name = "customer_id")]
    )
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    var roles: Set<Role> = setOf()

)