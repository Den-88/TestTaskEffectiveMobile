package com.den.shak.domain.usecase

import com.den.shak.domain.model.Vacancy
import com.den.shak.domain.repository.Repository

class GetFavoriteVacanciesUseCase(private val repository: Repository) {
    suspend fun execute(): List<Vacancy> {
        return repository.getFavoriteVacancies()
    }
}
