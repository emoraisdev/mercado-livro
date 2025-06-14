package com.mercadolivro.model;

import com.mercadolivro.enums.BookStatus
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
data class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {

            if (field == BookStatus.CANCELADO || field == BookStatus.DELETADO) {
                throw RuntimeException(
                    "Alteração do Status Não Permitida para" +
                            " Book com Status $field"
                )
            }

            field = value
        }

    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: Customer? = null,
        status: BookStatus?
    ) : this(id, name, price, customer) {
        this.status = status
    }

}