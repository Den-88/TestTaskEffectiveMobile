package com.den.shak.effectivemobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.den.shak.effectivemobile.R
import com.den.shak.effectivemobile.databinding.FragmentHomeBinding
import com.den.shak.effectivemobile.ui.adapters.OffersAdapter
import com.den.shak.effectivemobile.ui.adapters.VacancyAdapter
import com.den.shak.effectivemobile.ui.utils.VacancyViewUtils
import com.den.shak.effectivemobile.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var offersAdapter: OffersAdapter
    private lateinit var vacancyAdapter: VacancyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerOffersView()
        setupRecyclerView()

        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            offersAdapter.submitList(offers)
        }
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.submiPreviewtList(vacancies)
            VacancyViewUtils.updateButtonText(binding, vacancies.size)
        }
        binding.buttonMore.setOnClickListener {
            VacancyViewUtils.showAllVacancies(binding, requireContext(), viewModel, viewLifecycleOwner, vacancyAdapter)
        }
    }

    private fun setupRecyclerOffersView() {
        offersAdapter = OffersAdapter()
        binding.offersRecyclerView.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.offersAdapter
        }
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
            adapter = this@HomeFragment.vacancyAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

