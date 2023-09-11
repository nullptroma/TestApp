package com.example.testapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.usecases.GetFlowEnabledCardsUseCase
import com.example.testapp.domain.usecases.cards.UseCryptoCardUseCase
import com.example.testapp.domain.usecases.cards.UseMapCardUseCase
import com.example.testapp.domain.usecases.cards.UseWeatherCardUseCase
import com.example.testapp.presentation.states.screens.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val _getFlowEnabledCardsUseCase: GetFlowEnabledCardsUseCase,
    private val _useWeather: UseWeatherCardUseCase,
    private val _useMap: UseMapCardUseCase,
    private val _useCrypto: UseCryptoCardUseCase
) : ViewModel() {
    val state: StateFlow<MainScreenState>
        get() = _state
    private val _state = MutableStateFlow(MainScreenState())

    init {
        viewModelScope.launch {
            _getFlowEnabledCardsUseCase.flowData.collect {
                _state.value = _state.value.copy(cards = it, loading = false)
            }
        }
    }
}