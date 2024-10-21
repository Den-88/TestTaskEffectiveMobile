package com.den.shak.effectivemobile.ui.utils

import android.content.res.Resources
import android.util.TypedValue
import com.den.shak.effectivemobile.R

object OfferViewUtils {
    fun getIconResourceById(offerId: String?): Int? {
        return when (offerId) {
            "near_vacancies" -> R.drawable.ic_near_vacancies
            "level_up_resume" -> R.drawable.ic_level_up_resume
            "temporary_job" -> R.drawable.ic_temporary_job
            else -> null
        }
    }

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()
    }
}