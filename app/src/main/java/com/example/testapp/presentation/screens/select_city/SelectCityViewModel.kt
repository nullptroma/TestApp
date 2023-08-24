package com.example.testapp.presentation.screens.select_city

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testapp.MyObserver
import com.example.testapp.data.local.repositories.CitiesRepository
import com.example.testapp.domain.CityInfo
import com.example.testapp.presentation.settings.CitySettingBridge
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor(
    val repo: CitiesRepository
) : ViewModel() {
    val state: State<SelectCityScreenState>
        get() = _state
    private val _state = mutableStateOf(SelectCityScreenState())

    private lateinit var _bridge: CitySettingBridge
    private val _observer = MyObserver<List<CityInfo>> {
        _state.value = _state.value.copy(cities = it)
    }

    init {
        repo.getAll().observeForever(_observer)
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
        repo.getAll().removeObserver(_observer)
    }
}