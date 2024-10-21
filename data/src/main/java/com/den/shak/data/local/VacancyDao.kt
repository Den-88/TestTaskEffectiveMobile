package com.den.shak.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.den.shak.domain.model.Vacancy

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancies(vacancies: List<Vacancy>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: Vacancy)

    @Delete
    suspend fun deleteVacancy(vacancy: Vacancy)

    @Query("SELECT * FROM vacancies")
    suspend fun getAllVacancies(): List<Vacancy>

    @Query("SELECT * FROM vacancies WHERE id = :id LIMIT 1")
    suspend fun getVacancyById(id: String): Vacancy?
}
