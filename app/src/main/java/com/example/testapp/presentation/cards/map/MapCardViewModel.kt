package com.example.testapp.presentation.cards.map

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.MapSettings
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.domain.models.settings.CitySettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

class MapCardViewModel @AssistedInject constructor(
    private val _useMapSettingsUseCase: UseCardSettingsUseCase<MapSettings>,
    @Assisted id: Long
) : CardViewModel() {

    override val isSet: State<Boolean>
        get() = _isSet
    private val _isSet = mutableStateOf(false)

    override val id: Long
        get() = _id
    private var _id:Long

    val state: State<MapCardState>
        get() = _state
    private val _state = mutableStateOf(MapCardState())
    private var _setting : MapSettings = MapSettings()

    init {
        _id = id
        loadSetting()
    }

    private fun refreshFromSetting() {
        _state.value = _state.value.copy(coordinates = _setting.cityInfo.coordinates)
    }

    private fun loadSetting() {
        viewModelScope.launch {
            _setting = _useMapSettingsUseCase.read(_id).copy()
            refreshFromSetting()
            _isSet.value = _setting.cityInfo.name.isNotEmpty()
        }
    }

    private fun saveSetting() {
        viewModelScope.launch {
            _useMapSettingsUseCase.updateSetting(_id, _setting)
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
        fun create(id: Long): MapCardViewModel
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
fun mapCardViewModel(id: Long): MapCardViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, ViewModelFactoryProvider::class.java
    ).mapCardViewModelFactory()

    return viewModel(
        factory = MapCardViewModel.provideFactory(factory, id), key = id.toString()
    )
}