package com.mercadolivro.extension

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.model.Customer

fun PostCustomerRequest.toCustomerModel(): Customer {
    return Customer(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): Customer {
    return Customer(id = id, name = this.name, email = this.email)
}