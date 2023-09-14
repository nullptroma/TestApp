package com.example.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import com.example.testapp.presentation.viewmodels.SelectCityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectCityFragment : Fragment() {
    private val viewModel: SelectCityViewModel by viewModels()
    private var _binding: FragmentSelectCityBinding? = null
    private val binding get() = _binding!!
    private val menu
        get() = (activity as MainActivity).toolbar.menu
    private lateinit var adapter: CitiesAdapter
    private var _filterString = ""

    override fun onStart() {
        super.onStart()
        setMenuItems(true)
    }

    private fun setMenuItems(visible: Boolean) {
        val search = menu.findItem(R.id.search_button)
        search.isVisible = visible
        val searchAction = search.actionView as SearchView
        if (visible) {
            searchAction.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    _filterString = newText ?: ""
                    adapter.filter.filter(newText)
                    return true
                }
            })
        } else {
            searchAction.clearFocus()
            searchAction.setQuery("", false)
            searchAction.isIconified = true
            search.collapseActionView()
        }
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
        adapter = CitiesAdapter()
        adapter.onClick = {
            viewModel.selectCity(it)
        }
        binding.citiesRecyclerView.adapter = adapter

        binding.citiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { uiState ->
                    adapter.submit(uiState.cities, _filterString)

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