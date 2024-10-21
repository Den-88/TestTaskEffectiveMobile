package com.den.shak.effectivemobile.di

import com.den.shak.domain.usecase.GetFavoriteVacanciesUseCase
import com.den.shak.domain.usecase.GetOffersUseCase
import com.den.shak.domain.usecase.GetVacanciesUseCase
import com.den.shak.domain.usecase.ManageFavoriteVacancyUseCase
import com.den.shak.effectivemobile.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Регистрация репозиториев и других зависимостей
    single { GetVacanciesUseCase(get()) }
    single { GetOffersUseCase(get()) }
    single { GetFavoriteVacanciesUseCase(get()) }
    single { ManageFavoriteVacancyUseCase(get()) }

    // Регистрация ViewModel
    viewModel { HomeViewModel(get(), get(), get(), get()) } // передача всех нужных зависимостей
}
