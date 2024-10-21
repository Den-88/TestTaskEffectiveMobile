package com.den.shak.effectivemobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.den.shak.domain.model.Offer
import com.den.shak.domain.model.Vacancy
import com.den.shak.domain.usecase.GetFavoriteVacanciesUseCase
import com.den.shak.domain.usecase.GetOffersUseCase
import com.den.shak.domain.usecase.GetVacanciesUseCase
import com.den.shak.domain.usecase.ManageFavoriteVacancyUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getOffersUseCase: GetOffersUseCase,
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val getFavoriteVacanciesUseCase: GetFavoriteVacanciesUseCase,
    private val manageFavoriteVacancyUseCase: ManageFavoriteVacancyUseCase

) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    private val _vacancies = MutableLiveData<List<Vacancy>>()
    private val _favoriteVacancies = MutableLiveData<List<Vacancy>>()

    val offers: LiveData<List<Offer>> get() = _offers
    val vacancies: LiveData<List<Vacancy>> get() = _vacancies
    val favoriteVacancies: LiveData<List<Vacancy>> get() = _favoriteVacancies

    init {
        fetchOffers()
        fetchVacancies()
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            val offersList = getOffersUseCase.execute()
            _offers.value = offersList
        }
    }

    private fun fetchVacancies() {
        viewModelScope.launch {
            val vacanciesList = getVacanciesUseCase.execute()
            _vacancies.value = vacanciesList
            fetchFavoriteVacancies()
        }
    }

    private fun fetchFavoriteVacancies() {
        viewModelScope.launch {
            val favoriteVacanciesList = getFavoriteVacanciesUseCase.execute()
            _favoriteVacancies.value = favoriteVacanciesList
        }
    }

    fun onFavoriteClicked(vacancy: Vacancy) {
        viewModelScope.launch {
            val isFavorite = manageFavoriteVacancyUseCase.isVacancyFavorite(vacancy)
            if (isFavorite) {
                manageFavoriteVacancyUseCase.removeVacancyFromFavorites(vacancy)
            } else {
                manageFavoriteVacancyUseCase.addVacancyToFavorites(vacancy)
            }
            fetchVacancies()
        }
    }
}
