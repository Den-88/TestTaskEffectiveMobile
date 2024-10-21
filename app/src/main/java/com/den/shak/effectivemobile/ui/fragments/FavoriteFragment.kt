package com.den.shak.effectivemobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.den.shak.effectivemobile.R
import com.den.shak.effectivemobile.databinding.FragmentFavoriteBinding
import com.den.shak.effectivemobile.ui.adapters.VacancyAdapter
import com.den.shak.effectivemobile.ui.utils.VacancyTextUtils.getVacancyCountText
import com.den.shak.effectivemobile.viewmodel.HomeViewModel
import kotlin.getValue

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var vacancyAdapter: VacancyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        showFavoritesVacancies()
    }

    private fun setupRecyclerView() {
        vacancyAdapter = VacancyAdapter(
            onVacancyClick = { vacancy ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PlaceholderFragmentWithBack())
                    .addToBackStack(null)
                    .commit()
            },
            onFavoriteClick = { vacancy ->
                viewModel.onFavoriteClicked(vacancy)
            }
        )
        binding.vacancyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FavoriteFragment.vacancyAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showFavoritesVacancies() {
        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { favoriteVacancies ->
            vacancyAdapter.submitList(favoriteVacancies)
            binding.vacancyCount.text = getVacancyCountText(vacancyAdapter.itemCount)
        }
    }
}

