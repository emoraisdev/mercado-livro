package com.mercadolivro.fundamentos

data class Pessoa(var nome: String, var idade: Int) {

    override fun toString(): String {
        return "Classe: Pessoa. ${nome}, Idade: ${idade}"
    }
}

fun main() {
    var gustavo = Pessoa("Gustavo", 25)
    println(gustavo)
}