package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.enums.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val repo: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
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
        val customerSave = customer.copy(
            roles = setOf(Role.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        repo.save(customerSave)
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