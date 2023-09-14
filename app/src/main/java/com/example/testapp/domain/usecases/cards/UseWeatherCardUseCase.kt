package com.example.testapp.domain.usecases.cards

import com.example.testapp.domain.models.Weather
import com.example.testapp.domain.models.cards.WeatherCardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cards_callbacks.WeatherCallback
import com.example.testapp.domain.models.cardsettings.WeatherSettings
import com.example.testapp.domain.models.city.CityInfo
import com.example.testapp.domain.models.settings.CitySettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.cardsdata.GetWeatherUseCase
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseWeatherCardUseCase @Inject constructor(
    useWeatherSettingsUseCase: UseCardSettingsUseCase<WeatherSettings>,
    private val _getWeatherUseCase: GetWeatherUseCase,
) : UseCardUseCase<WeatherSettings>(useWeatherSettingsUseCase) {
    override val callbackContainer: ICardCallback = WeatherCallback { id ->
        CoroutineScope(Dispatchers.Default).launch {
            fetchData(id)
        }
    }

    override val state: StateFlow<Map<Long, WeatherCardState>>
        get() = _state
    private val _state = MutableStateFlow(mapOf<Long, WeatherCardState>())

    private suspend fun fetchData(id: Long) {
        val set = settings[id] ?: return
        if (set.cityInfo.name.isEmpty()) {
            _state.value = _state.value.toMutableMap().apply {
                this[id] = WeatherCardState(id, null, needSettings = true)
            }
            return
        }
        _state.value = _state.value.toMutableMap().apply {
            this[id] = WeatherCardState(id, null, needSettings = false)
        }
        var res: Weather? = null
        while (res == null) {
            res = _getWeatherUseCase.get(set.cityInfo.coordinates)
            if (res == null)
                delay(1000)
        }
        _state.value = _state.value.toMutableMap().apply {
            this[id] = WeatherCardState(id, res)
        }
    }

    private suspend fun fetchAll() {
        for (set in settings) {
            CoroutineScope(Dispatchers.Default).launch {
                fetchData(set.key)
            }
        }
    }

    private fun setCity(id: Long, city: CityInfo) {
        if (!settings.containsKey(id))
            return
        updateSettingForCard(id, settings[id]!!.copy(cityInfo = city))
    }

    override fun createSettingBridge(id: Long): SettingBridge {
        return CitySettingBridge { city ->
            setCity(id, city)
        }
    }

    override suspend fun onSettingsChange() {
        checkEntries()
        fetchAll()
    }

    override suspend fun onSettingsChange(id:Long) {
        fetchData(id)
    }

    private fun checkEntries() {
        val add = settings.filter { pair -> !state.value.containsKey(pair.key) }
        _state.value = _state.value.toMutableMap().apply {
            this += add.map {
                it.key to WeatherCardState(
                    it.key,
                    null,
                    needSettings = it.value.cityInfo.name.isEmpty()
                )
            }
        }.filter { pair -> settings.containsKey(pair.key) }
    }
}
