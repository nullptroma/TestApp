package com.example.testapp.presentation.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
import com.example.testapp.databinding.FragmentMainBinding
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.presentation.activities.MainActivity
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.presentation.viewmodels.cards.WeatherCardViewModel
import com.example.testapp.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    @Inject
    lateinit var weatherViewModelFactory: WeatherCardViewModel.Factory
    private val _viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val menu
        get() = (activity as MainActivity).toolbar.menu

    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        setMenuItems(true)
    }

    private fun setMenuItems(visible: Boolean) {
        val textButton = menu.findItem(R.id.text_button)
        textButton.isVisible = visible
        if (visible) {
            textButton.title = "Правка"
            textButton.setOnMenuItemClickListener {
                findNavController().navigate(R.id.action_Menu_to_MenuSettingsFragment)
                true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        val vmStore = this.viewModelStore
        val cardViewModels = MutableStateFlow(listOf<CardViewModel>())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    _viewModel.state.collect { uiState ->
                        cardViewModels.value = uiState.cards.map {
                            var vm = vmStore[it.id.toString()]
                            if(vm != null)
                                return@map vm as WeatherCardViewModel
                            val factory = EntryPointAccessors.fromActivity(
                                this@MainFragment.activity as Activity, ViewModelFactoryProvider::class.java
                            ).weatherCardViewModelFactory()
                            vm = factory.create(it.id)
                            vmStore.put(it.id.toString(), vm)
                            return@map vm
                        }
                        Log.d("MyTag", "Vms size: ${cardViewModels.value.size}, ids: ${uiState.cards.map { it.id }}")
                    }
                }
                launch {
                    cardViewModels.collect {
                        Log.d("MyTag", "Vms: ${
                            it.map { vm ->
                                "${vm.state.value.type},${vm.id}"
                            }
                        }")
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