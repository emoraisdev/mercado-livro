package com.mercadolivro.service

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.model.Purchase
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val repo: PurchaseRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun create(purchase: Purchase){
        repo.save(purchase)

        eventPublisher.publishEvent(PurchaseEvent(this, purchase))
    }

    fun update(purchase: Purchase) {
        repo.save(purchase)
    }

}