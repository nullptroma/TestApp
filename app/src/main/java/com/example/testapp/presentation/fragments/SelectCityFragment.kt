package com.example.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentSelectCityBinding
import com.example.testapp.presentation.activities.MainActivity
import com.example.testapp.presentation.adapters.CitiesAdapter
import com.example.testapp.presentation.screens.select_city.SelectCityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectCityFragment : Fragment() {
    private val viewModel: SelectCityViewModel by viewModels()
    private var _binding: FragmentSelectCityBinding? = null
    private val binding get() = _binding!!
    private val menu
        get() = (activity as MainActivity).toolbar.menu


    override fun onStart() {
        super.onStart()
        setMenuItems(true)
    }

    private fun setMenuItems(visible: Boolean) {
        menu.findItem(R.id.search_button).isVisible = visible
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSelectCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.citiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { uiState ->
                    val adapter = CitiesAdapter(uiState.cities)
                    adapter.onClick = {
                        viewModel.selectCity(it)
                    }
                    binding.citiesRecyclerView.adapter = adapter

                    if (uiState.exit) {
                        viewModel.restoreExit()
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        setMenuItems(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}