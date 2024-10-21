package com.den.shak.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.den.shak.domain.model.Converters
import com.den.shak.domain.model.Vacancy

@Database(entities = [Vacancy::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VacancyDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}