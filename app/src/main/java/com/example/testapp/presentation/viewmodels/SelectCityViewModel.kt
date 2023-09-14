package com.example.testapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.models.city.CityInfo
import com.example.testapp.domain.models.settings.CitySettingBridge
import com.example.testapp.domain.models.settings.SettingBridgeContainer
import com.example.testapp.domain.usecases.GetFlowCitiesUseCate
import com.example.testapp.presentation.states.screens.SelectCityScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor(
    useCase: GetFlowCitiesUseCate,
    private val _bridgeContainer: SettingBridgeContainer
) : ViewModel() {
    val state: StateFlow<SelectCityScreenState>
        get() = _state
    private val _state = MutableStateFlow(SelectCityScreenState())

    private val _bridge
        get() = _bridgeContainer.bridge as? CitySettingBridge

    init {
        viewModelScope.launch {
            useCase.flowData.collect {
                _state.value = SelectCityScreenState(it)
            }
        }
    }

    fun restoreExit() {
        setExit(false)
    }

    fun selectCity(city: CityInfo) {
        _bridge?.city = city
        _bridge?.invokeCallback()
        setExit(true)
    }

    private fun setExit(value: Boolean) {
        _state.value = _state.value.copy(exit = value)
    }
}