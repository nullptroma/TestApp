package com.example.testapp.presentation.cards.weather

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.data.local.repositories.WeatherSettingsRepository
import com.example.testapp.data.remote.repositories.WeatherRepository
import com.example.testapp.di.IoDispatcher
import com.example.testapp.di.MainDispatcher
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.domain.cardsettings.WeatherSettings
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.presentation.settings.CitySettingBridge
import com.example.testapp.presentation.settings.SettingBridge
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherCardViewModel @AssistedInject constructor(
    private val _repo: WeatherSettingsRepository,
    private val _dataRepo: WeatherRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @Assisted id: Long
) : CardViewModel() {

    override val isSet: State<Boolean>
        get() = _isSet
    private val _isSet = mutableStateOf(false)

    override val id: Long
        get() = _id
    private var _id: Long

    val state: State<WeatherCardState>
        get() = _state
    private val _state = mutableStateOf(WeatherCardState())
    private var _setting: WeatherSettings = WeatherSettings()

    init {
        _id = id
        loadSetting()
    }

    private fun refreshFromSetting() {
        _isSet.value = _setting.cityInfo.name.isNotEmpty()

        if (_isSet.value) {
            fetchData()
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            var info = _dataRepo.get(_setting.cityInfo.coordinates)?.copy(city = _setting.cityInfo.name)
            _state.value = WeatherCardState(data = info)
        }
    }

    private fun loadSetting() {
        viewModelScope.launch(ioDispatcher) {
            _setting = _repo.getById(_id).copy()
            withContext(mainDispatcher) {
                refreshFromSetting()
            }
        }
    }

    private fun saveSetting() {
        viewModelScope.launch(ioDispatcher) {
            _repo.updateSetting(_id, _setting)
            loadSetting()
        }
    }

    private fun setCity(city: CityInfo) {
        _setting.cityInfo = city
        saveSetting()
    }

    override fun createSettingBridge(): SettingBridge {
        return CitySettingBridge { city ->
            setCity(city)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): WeatherCardViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}

@Composable
fun weatherCardViewModel(id: Long): WeatherCardViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, ViewModelFactoryProvider::class.java
    ).weatherCardViewModelFactory()

    return viewModel(
        factory = WeatherCardViewModel.provideFactory(factory, id), key = id.toString()
    )
}