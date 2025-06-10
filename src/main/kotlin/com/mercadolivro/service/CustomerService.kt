package com.mercadolivro.service

import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class CustomerService(
    val repo: CustomerRepository,
    val bookService: BookService
) {


    fun getAll(name: String?): List<Customer> {
        name?.let {
            return repo.findByNameContaining(it)
        }
        return repo.findAll().toList()
    }

    fun getById(id: Int): Customer {
        return repo.findById(id).orElseThrow()
    }

    fun create(customer: Customer) {
        repo.save(customer)
    }

    fun update(customer : Customer) {

        if (!repo.existsById(customer.id!!)){
            throw Exception("Customer Não Encontrado.");
        }

        repo.save(customer)
    }

    fun delete(id: Int) {

        val customer = getById(id)

        bookService.deleteByCustomer(customer)

        repo.deleteById(id)
    }
}