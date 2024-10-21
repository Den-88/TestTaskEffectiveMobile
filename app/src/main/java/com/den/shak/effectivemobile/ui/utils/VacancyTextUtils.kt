package com.den.shak.effectivemobile.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object VacancyTextUtils {
    fun getPeopleText(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "человек"
            count % 10 in 2..4 && (count % 100 !in 12..14) -> "человека"
            else -> "человек"
        }
    }

    fun getVacancyCountText(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "$count вакансия"
            count % 10 in 2..4 && (count % 100 !in 12..14) -> "$count вакансии"
            else -> "$count вакансий"
        }
    }

    fun getVacancyButtonText(vacancyCount: Int): String {
        return when (vacancyCount) {
            1 -> "Ещё 1 вакансия"
            2 -> "Ещё 2 вакансии"
            3 -> "Ещё 3 вакансии"
            else -> "Ещё ${vacancyCount} вакансий"
        }
    }

    fun getPeopleViewingText(number: Int): String {
        return if (number > 0) {
            val peopleText = getPeopleText(number)
            "Сейчас просматривает $number $peopleText"
        } else {
            ""
        }
    }

    fun formatPublishedDate(publishedDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val date = LocalDate.parse(publishedDate, formatter)
        val day = date.dayOfMonth
        val month = when (date.monthValue) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        }
        return "Опубликовано $day $month"
    }
}
