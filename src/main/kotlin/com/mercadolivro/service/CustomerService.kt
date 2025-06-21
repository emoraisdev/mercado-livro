package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

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
        return repo.findById(id).orElseThrow {
            NotFoundException(
                Errors.ML2001.message.format(id),
                Errors.ML2001.code
            )
        }
    }

    fun create(customer: Customer) {
        repo.save(customer)
    }

    fun update(customer: Customer) {

        if (!repo.existsById(customer.id!!)) {
            throw Exception("Customer Não Encontrado.");
        }

        repo.save(customer)
    }

    fun delete(id: Int) {

        val customer = getById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO
        repo.save(customer)
    }

    fun isEmailAvaiable(email: String): Boolean {
        return !repo.existsByEmail(email)
    }
}