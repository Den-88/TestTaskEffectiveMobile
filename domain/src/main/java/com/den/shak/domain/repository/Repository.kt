package com.den.shak.domain.repository

import com.den.shak.domain.model.Offer
import com.den.shak.domain.model.Vacancy

interface Repository {
    suspend fun getOffers(): List<Offer>
    suspend fun getVacancies(): List<Vacancy>
    suspend fun getFavoriteVacancies(): List<Vacancy>
    suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean
    suspend fun addVacancyToFavorites(vacancy: Vacancy)
    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)
}
