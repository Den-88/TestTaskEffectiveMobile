package com.den.shak.effectivemobile.ui.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.den.shak.effectivemobile.R
import com.den.shak.effectivemobile.databinding.FragmentHomeBinding
import com.den.shak.effectivemobile.ui.adapters.VacancyAdapter
import com.den.shak.effectivemobile.ui.utils.VacancyTextUtils.getVacancyButtonText
import com.den.shak.effectivemobile.ui.utils.VacancyTextUtils.getVacancyCountText
import com.den.shak.effectivemobile.viewmodel.HomeViewModel

object VacancyViewUtils {
    fun updateButtonText(binding: FragmentHomeBinding, vacancyCount: Int) {
        binding.buttonMore.text = getVacancyButtonText(vacancyCount)
        binding.buttonMore.visibility = if (vacancyCount > 3) View.VISIBLE else View.GONE
    }

    fun showAllVacancies(
        binding: FragmentHomeBinding,
        context: Context,
        viewModel: HomeViewModel,
        viewLifecycleOwner: LifecycleOwner,
        vacancyAdapter: VacancyAdapter
    ) {
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.submitList(vacancies)
        }
        binding.searchLayout.startIconDrawable = ContextCompat.getDrawable(
            binding.root.context,
            R.drawable.ic_back
        )
        binding.searchEditText.hint =
            context.getString(R.string.vacancy_view_utils_hint_all_vacansies)
        binding.filterLayout.visibility = View.VISIBLE
        binding.buttonMore.visibility = View.GONE
        binding.offersRecyclerView.visibility = View.GONE
        binding.vacancyCount.text = getVacancyCountText(vacancyAdapter.itemCount)
        binding.vacancyTitle.visibility = View.GONE
        binding.searchLayout.setStartIconOnClickListener {
            showPreviewVacancies(binding, context, viewModel, viewLifecycleOwner, vacancyAdapter)
        }
    }

    fun showPreviewVacancies(
        binding: FragmentHomeBinding,
        context: Context,
        viewModel: HomeViewModel,
        viewLifecycleOwner: LifecycleOwner,
        vacancyAdapter: VacancyAdapter
    ) {
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.submiPreviewtList(vacancies)
        }
        binding.searchLayout.startIconDrawable = ContextCompat.getDrawable(
            binding.root.context,
            R.drawable.ic_search
        )
        binding.searchEditText.hint =
            context.getString(R.string.vacancy_view_utils_hint_preview_vacansies)
        binding.filterLayout.visibility = View.GONE
        binding.buttonMore.visibility = View.VISIBLE
        binding.offersRecyclerView.visibility = View.VISIBLE
        binding.vacancyTitle.visibility = View.VISIBLE
    }
}
