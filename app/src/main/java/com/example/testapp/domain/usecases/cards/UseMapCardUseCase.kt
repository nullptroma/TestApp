package com.example.testapp.domain.usecases.cards

import android.util.Log
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.MapSettings
import com.example.testapp.domain.models.settings.CitySettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.presentation.states.cards.MapCardState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseMapCardUseCase @Inject constructor(
    useMapSettingsUseCase: UseCardSettingsUseCase<MapSettings>,
    @IoDispatcher private val _ioDispatcher : CoroutineDispatcher
) : UseCardUseCase<MapSettings>(useMapSettingsUseCase, _ioDispatcher) {
    override val state: StateFlow<Map<Long, MapCardState>>
        get() = _state
    private val _state = MutableStateFlow(mapOf<Long, MapCardState>())

    init {
        loadSetting()
    }

    private fun refreshFromSetting() {
    }

    private fun loadSetting() {

    }

    private fun saveSetting() {
    }

    private fun setCity(city: CityInfo) {
    }

    override fun createSettingBridge(id:Long): SettingBridge {
        return CitySettingBridge { city ->
            setCity(city)
        }
    }

    override fun onSettingsChange() {
        Log.d("MyTag", "Map sets: ${settings.map { it.key }}")
    }
}

