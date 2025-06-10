package com.mercadolivro.repository

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, Int> {

    fun findByStatus(ativo: BookStatus): List<Book>

    fun findByCustomer(customer: Customer): List<Book>

}