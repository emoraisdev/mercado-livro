package com.mercadolivro.enums

enum class Errors(
    val code: String,
    val message: String
) {
    ML1000("ML-1000", "Unauthorized"),
    ML1001("ML-1001", "Book %s not exists"),
    ML1002("ML-1002", "Cannot upadte Book with status [%s]"),

    ML2001("ML-2001", "Customer %s not exists"),

    ML3001("ML-3001", "Invalid Request")
}