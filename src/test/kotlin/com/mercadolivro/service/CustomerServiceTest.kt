package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @MockK
    private lateinit var repo: CustomerRepository
    @MockK
    private lateinit var bookService: BookService
    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @Test
    fun `should return all customers`() {

        val customersMock = listOf(buildCustomer(), buildCustomer());

        every { repo.findAll() } returns customersMock

        val customers = customerService.getAll(null)

        assertEquals(customersMock, customers)
        verify(exactly = 1) { repo.findAll() }
        verify(exactly = 0) { repo.findByNameContaining(any()) }
    }

    @Test
    fun `should return customers when name is informed`() {

        val name = UUID.randomUUID().toString()
        val customersMock = listOf(buildCustomer(), buildCustomer());

        every { repo.findByNameContaining(name) } returns customersMock

        val customers = customerService.getAll(name)

        assertEquals(customersMock, customers)
        verify(exactly = 0) { repo.findAll() }
        verify(exactly = 1) { repo.findByNameContaining(name) }
    }

    private fun buildCustomer(
        id: Int? = null,
        name: String = "Customer Name",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
    ) = Customer(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        roles = setOf(Role.CUSTOMER)
    )

}