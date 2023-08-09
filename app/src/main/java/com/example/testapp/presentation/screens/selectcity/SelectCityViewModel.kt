package com.example.testapp.presentation.screens.selectcity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testapp.presentation.settings.CitySettingBridge
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor() : ViewModel() {
    val state: State<SelectCityScreenState>
        get() = _state
    private val _state = mutableStateOf(SelectCityScreenState())

    private lateinit var _bridge: CitySettingBridge

    fun restoreExit() {
        setExit(false)
    }

    fun selectCity(city: String) {
        _bridge.city = city
        _bridge.invokeCallback()
        setExit(true)
    }

    fun setBridge(bridge: CitySettingBridge) {
        _bridge = bridge
    }

    private fun setExit(value: Boolean) {
        _state.value = _state.value.copy(exit = value)
    }
}