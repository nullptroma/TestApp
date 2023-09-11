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
import com.example.testapp.databinding.FragmentMainBinding
import com.example.testapp.domain.models.cardsettings.CardSettings
import com.example.testapp.domain.usecases.cards.UseCardUseCase
import com.example.testapp.presentation.activities.MainActivity
import com.example.testapp.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {
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

        val useCardsUseCase = MutableStateFlow(listOf<UseCardUseCase<CardSettings>>())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    _viewModel.state.collect {

                    }
                }
                launch {
                    useCardsUseCase.collect {

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