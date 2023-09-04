package com.example.testapp.presentation.screens.select_city

import androidx.lifecycle.ViewModel
import com.example.testapp.MyObserver
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.usecases.GetLiveCitiesUseCate
import com.example.testapp.presentation.settings.CitySettingBridge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor(
    private val useCase: GetLiveCitiesUseCate
) : ViewModel() {
    val state: StateFlow<SelectCityScreenState>
        get() = _state
    private val _state = MutableStateFlow(SelectCityScreenState())

    private lateinit var _bridge: CitySettingBridge
    private val _observer = MyObserver<List<CityInfo>> {
        _state.value = _state.value.copy(cities = it)
    }

    init {
        useCase.liveData.observeForever(_observer)
    }

    fun restoreExit() {
        setExit(false)
    }

    fun selectCity(city: CityInfo) {
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

    override fun onCleared() {
        super.onCleared()
        useCase.liveData.removeObserver(_observer)
    }
}