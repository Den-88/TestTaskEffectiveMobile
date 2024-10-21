package com.den.shak.data.repository

import com.den.shak.data.local.VacancyDao
import com.den.shak.data.network.ApiService
import com.den.shak.domain.model.Offer
import com.den.shak.domain.model.Vacancy
import com.den.shak.domain.repository.Repository

class ResponseRepositoryImpl(
    private val apiService: ApiService,
    private val vacancyDao: VacancyDao,
    private var cachedVacancies: List<Vacancy>? = null

) : Repository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        vacancyDao.insertVacancy(vacancy.copy(isFavorite = true))
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        vacancyDao.deleteVacancy(vacancy)
    }

    override suspend fun getFavoriteVacancies(): List<Vacancy> {
        return vacancyDao.getAllVacancies().filter { it.isFavorite }
    }

    override suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return vacancyDao.getVacancyById(vacancy.id)?.isFavorite ?: false
    }

    override suspend fun getVacancies(): List<Vacancy> {
        val apiVacancies: List<Vacancy>? = if (cachedVacancies != null) null else apiService.getApiResponse().vacancies
        val favoriteVacancies = vacancyDao.getAllVacancies()
        val vacanciesToInsert = mutableListOf<Vacancy>()

        val combinedVacancies = if (cachedVacancies != null)
            cachedVacancies?.map { apiVacancy ->
                val isFavorite = favoriteVacancies.any { it.id == apiVacancy.id }
                if (isFavorite && !vacanciesToInsert.contains(apiVacancy)) {
                    vacanciesToInsert.add(apiVacancy.copy(isFavorite = true))
                }
                apiVacancy.copy(isFavorite = isFavorite)
            } else
            apiVacancies?.map { apiVacancy ->
                if (apiVacancy.isFavorite && !favoriteVacancies.any { it.id == apiVacancy.id }) {
                    vacancyDao.insertVacancy(apiVacancy.copy(isFavorite = true))
                    apiVacancy.copy(isFavorite = true)
                } else {
                    val isFavorite = favoriteVacancies.any { it.id == apiVacancy.id }
                    if (isFavorite && !vacanciesToInsert.contains(apiVacancy)) {
                        vacanciesToInsert.add(apiVacancy.copy(isFavorite = true))
                    }
                    apiVacancy.copy(isFavorite = isFavorite)
                }
            }

        if (vacanciesToInsert.isNotEmpty()) {
            vacancyDao.insertVacancies(vacanciesToInsert)
        }

        cachedVacancies = combinedVacancies

        return combinedVacancies?: emptyList()
    }

    override suspend fun getOffers(): List<Offer> {
        val offers = apiService.getApiResponse().offers
        return offers
    }
}
