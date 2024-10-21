package com.den.shak.domain.usecase

import com.den.shak.domain.model.Vacancy
import com.den.shak.domain.repository.Repository

class ManageFavoriteVacancyUseCase(private val repository: Repository) {
    suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return repository.isVacancyFavorite(vacancy)
    }

    suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        repository.addVacancyToFavorites(vacancy)
    }

    suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        repository.removeVacancyFromFavorites(vacancy)
    }
}
