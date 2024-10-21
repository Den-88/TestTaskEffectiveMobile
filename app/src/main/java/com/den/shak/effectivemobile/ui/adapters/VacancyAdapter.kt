package com.den.shak.effectivemobile.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.den.shak.domain.model.Vacancy
import com.den.shak.effectivemobile.R
import com.den.shak.effectivemobile.databinding.ItemVacancyBinding
import com.den.shak.effectivemobile.ui.utils.VacancyTextUtils
import com.den.shak.effectivemobile.ui.utils.VacancyTextUtils.formatPublishedDate

class VacancyAdapter(
    private val onVacancyClick: (Vacancy) -> Unit,
    private val onFavoriteClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    val vacancies = mutableListOf<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size

    @SuppressLint("NotifyDataSetChanged")
    fun submiPreviewtList(vacancies: List<Vacancy>?) {
        this.vacancies.clear()
        vacancies?.let {
            this.vacancies.addAll(it.take(3))
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(vacancies: List<Vacancy>?) {
        this.vacancies.clear()
        vacancies?.let {
            this.vacancies.addAll(it)
        }
        notifyDataSetChanged()
    }

    inner class VacancyViewHolder(private val binding: ItemVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vacancy: Vacancy) {
            binding.root.setOnClickListener {
                onVacancyClick(vacancy)
            }

            binding.favoriteIcon.setOnClickListener {
                onFavoriteClick(vacancy)
            }
            vacancy.lookingNumber.let { number ->
                if (number > 0) {
                    binding.lookingNumber.text = VacancyTextUtils.getPeopleViewingText(number)
                    binding.lookingNumber.visibility = View.VISIBLE
                } else {
                    binding.lookingNumber.visibility = View.GONE
                }
            }

            binding.title.text = vacancy.title
            binding.company.text = vacancy.company
            binding.town.text = vacancy.address.town
            binding.experience.text = vacancy.experience.previewText
            binding.publishedDate.text = formatPublishedDate(vacancy.publishedDate)

            if (vacancy?.isFavorite == true) {
                binding.favoriteIcon.setImageResource(R.drawable.ic_favorites_fill)
            } else {
                binding.favoriteIcon.setImageResource(R.drawable.ic_favorites)
            }
        }
    }
}