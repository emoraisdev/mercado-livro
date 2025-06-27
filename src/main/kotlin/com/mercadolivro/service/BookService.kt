package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val repo: BookRepository
) {

    fun create(book: Book) {
        repo.save(book)
    }

    fun findAll(pageable: Pageable): Page<Book> {
        return repo.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<Book> =
        repo.findByStatus(BookStatus.ATIVO, pageable)

    fun findById(id: Int): Book {
        return repo.findById(id).orElseThrow {
            NotFoundException(
                Errors.ML1001.message.format(id),
                Errors.ML1001.code
            )
        }
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

    fun findAllByIds(bookIds: Set<Int>): List<Book> {
        return repo.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<Book>) {
        books.map { it.status = BookStatus.VENDIDO }

        repo.saveAll(books)
    }
}
