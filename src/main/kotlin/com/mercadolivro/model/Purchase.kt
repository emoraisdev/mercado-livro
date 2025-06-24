package com.mercadolivro.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "purchase")
data class Purchase(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,

    @ManyToMany
    @JoinTable(
        name = "purchase_book",
        joinColumns = [JoinColumn(name = "purchase_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: List<Book>,

    @Column
    val nfe: String? = null,

    @Column
    val price: BigDecimal,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
)