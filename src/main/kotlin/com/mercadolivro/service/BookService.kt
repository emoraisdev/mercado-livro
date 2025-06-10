package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val repo: BookRepository
) {

    fun create(book: Book) {
        repo.save(book)
    }

    fun findAll(): List<Book> {
        return repo.findAll().toList()
    }

    fun findActives(): List<Book> =
        repo.findByStatus(BookStatus.ATIVO)

    fun findById(id: Int): Book {
        return repo.findById(id).orElseThrow()
    }

    fun delete(id: Int) {

        val book = findById(id)
        book.status = BookStatus.CANCELADO

        update(book)
    }

    fun update(book: Book) {
        repo.save(book)
    }

    fun deleteByCustomer(customer: Customer) {

        val books = repo.findByCustomer(customer)

        for (book in books) {
            book.status = BookStatus.DELETADO
        }

        repo.saveAll(books)
    }
}
