package com.example.testapp.domain.usecases.cards

import com.example.testapp.domain.models.cards.MapCardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cardsettings.MapSettings
import com.example.testapp.domain.models.city.CityInfo
import com.example.testapp.domain.models.settings.CitySettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseMapCardUseCase @Inject constructor(
    useMapSettingsUseCase: UseCardSettingsUseCase<MapSettings>,
) : UseCardUseCase<MapSettings>(useMapSettingsUseCase) {
    override val callbackContainer: ICardCallback? = null
    override val state: StateFlow<Map<Long, MapCardState>>
        get() = _state
    private val _state = MutableStateFlow(mapOf<Long, MapCardState>())

    private fun refreshState() {
        val map = mutableMapOf<Long, MapCardState>()
        for(pair in settings)
            map[pair.key] = MapCardState(pair.key, pair.value.cityInfo.coordinates, needSettings = pair.value.cityInfo.name.isEmpty())
        _state.value = map
    }

    private fun setCity(id: Long, city: CityInfo) {
        if (!settings.containsKey(id))
            return
        updateSettingForCard(id, settings[id]!!.copy(cityInfo = city))
    }

    override fun createSettingBridge(id:Long): SettingBridge {
        return CitySettingBridge { city ->
            setCity(id, city)
        }
    }

    override suspend fun onSettingsChange() {
        refreshState()
    }

    override suspend fun onSettingsChange(id:Long) {
        refreshState()
    }
}

