package com.example.testapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentSelectCryptosBinding
import com.example.testapp.presentation.activities.MainActivity
import com.example.testapp.presentation.adapters.CryptosAdapter
import com.example.testapp.presentation.viewmodels.SelectCryptosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectCryptosFragment : Fragment() {
    private val _viewModel: SelectCryptosViewModel by viewModels()
    private var _binding: FragmentSelectCryptosBinding? = null
    private val binding get() = _binding!!
    private val menu
        get() = (activity as MainActivity).toolbar.menu
    private lateinit var _adapter: CryptosAdapter
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
                    _adapter.filter.filter(newText)
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

        _binding = FragmentSelectCryptosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _adapter = CryptosAdapter()
        _adapter.onChangeSelect = { id ->
            _viewModel.changeIdSelect(id)
        }
        binding.cryptosRecyclerView.adapter = _adapter
        binding.cryptosRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                _viewModel.state.collect { uiState ->
                    _adapter.submit(uiState.list, _filterString)
                    if(uiState.showToast) {
                        _viewModel.showToast(false)
                        Toast.makeText(
                            requireContext(), getText(R.string.toast_msg), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        setMenuItems(false)
        _viewModel.save()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}