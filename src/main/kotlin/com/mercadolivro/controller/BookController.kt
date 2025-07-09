package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.PageResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    private val customerService: CustomerService,
    private val service: BookService,
) {

    @GetMapping
    fun getAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse> {
        return service.findAll(pageable)
            .map { it.toResponse() }.toPageResponse()
    }

    @GetMapping("/active")
    fun getActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
        return service.findActives(pageable)
            .map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): BookResponse {
        return service.findById(id).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostBookRequest) {
        val customer = customerService.getById(request.customerId)
        service.create(request.toBookModel(customer))
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest) {

        val bookSaved = service.findById(id)
        service.update(book.toBookModel(bookSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        service.delete(id)
    }
}