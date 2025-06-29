package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvaiable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(

    @field:NotEmpty(message = "Nome deve ser informado.")
    var name: String,

    @field:Email(message = "E-mail deve ser válido.")
    @EmailAvaiable
    var email: String,

    @field:NotEmpty(message = "Senha deve ser informada.")
    var password: String
)