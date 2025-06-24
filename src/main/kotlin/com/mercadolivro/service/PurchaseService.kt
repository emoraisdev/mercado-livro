package com.mercadolivro.service

import com.mercadolivro.model.Purchase
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val repo: PurchaseRepository
) {

    fun create(purchase: Purchase){
        repo.save(purchase)
    }
}