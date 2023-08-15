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
import com.example.testapp.data.local.repositories.MapSettingsRepository
import com.example.testapp.di.IoDispatcher
import com.example.testapp.di.MainDispatcher
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.domain.cardsettings.MapSettings
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

class MapCardViewModel @AssistedInject constructor(
    private val _repo: MapSettingsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @Assisted id: Long
) : CardViewModel() {

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
        _state.value = _state.value.copy(city = _setting.city)
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
            refreshFromSetting()
        }
    }

    private fun setCity(city:String) {
        _setting.city = city
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
            assistedFactory: Factory, // this is the Factory interface
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