package com.den.shak.domain.usecase

import com.den.shak.domain.model.Offer
import com.den.shak.domain.repository.Repository

class GetOffersUseCase(private val repository: Repository) {
    suspend fun execute(): List<Offer> {
        return repository.getOffers()
    }
}
