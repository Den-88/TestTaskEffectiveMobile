package com.den.shak.effectivemobile.di

import androidx.room.Room
import com.den.shak.data.local.VacancyDao
import com.den.shak.data.local.VacancyDatabase
import com.den.shak.data.network.ApiService
import com.den.shak.data.repository.ResponseRepositoryImpl
import com.den.shak.domain.repository.Repository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://drive.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single<VacancyDao> { get<VacancyDatabase>().vacancyDao() }

    single {
        Room.databaseBuilder(get(), VacancyDatabase::class.java, "vacancy_db")
            .build()
    }

    single<Repository> { ResponseRepositoryImpl(get(), get()) }
}
