package com.den.shak.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAddress(address: Address): String {
        return gson.toJson(address)
    }

    @TypeConverter
    fun toAddress(addressString: String): Address {
        val type = object : TypeToken<Address>() {}.type
        return gson.fromJson(addressString, type)
    }

    @TypeConverter
    fun fromExperience(experience: Experience): String {
        return gson.toJson(experience)
    }

    @TypeConverter
    fun toExperience(experienceString: String): Experience {
        val type = object : TypeToken<Experience>() {}.type
        return gson.fromJson(experienceString, type)
    }

    @TypeConverter
    fun fromSalary(salary: Salary): String {
        return gson.toJson(salary)
    }

    @TypeConverter
    fun toSalary(salaryString: String): Salary {
        val type = object : TypeToken<Salary>() {}.type
        return gson.fromJson(salaryString, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(listString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(listString, type)
    }
}
