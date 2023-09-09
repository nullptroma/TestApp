package com.example.testapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.usecases.GetFlowEnabledCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFlowEnabledCardsUseCase: GetFlowEnabledCardsUseCase
) : ViewModel() {
    val state: StateFlow<MainScreenState>
        get() = _state
    private val _state = MutableStateFlow(MainScreenState())

    init {
        viewModelScope.launch {
            getFlowEnabledCardsUseCase.flowData.collect {
                _state.value = _state.value.copy(cards = it, loading = false)
            }
        }
    }
}