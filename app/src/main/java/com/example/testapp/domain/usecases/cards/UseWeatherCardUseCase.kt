package com.example.testapp.domain.usecases.cards

import android.util.Log
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.WeatherSettings
import com.example.testapp.domain.models.settings.CitySettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.cardsdata.GetWeatherUseCase
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.presentation.states.cards.WeatherCardState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseWeatherCardUseCase @Inject constructor(
    useWeatherSettingsUseCase: UseCardSettingsUseCase<WeatherSettings>,
    private val _getWeatherUseCase: GetWeatherUseCase,
    @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) : UseCardUseCase<WeatherSettings>(useWeatherSettingsUseCase, _ioDispatcher) {
    override val state: StateFlow<Map<Long, WeatherCardState>>
        get() = _state
    private val _state = MutableStateFlow(mapOf<Long, WeatherCardState>())

    private fun refreshFromSetting() {

    }

    fun fetchData() {

    }

    private fun saveSetting() {

    }

    private fun setCity(id: Long, city: CityInfo) {

    }

    override fun createSettingBridge(id: Long): SettingBridge {
        return CitySettingBridge { city ->
            setCity(id, city)
        }
    }

    override fun onSettingsChange() {
        Log.d("MyTag", "Weather sets: ${settings.map { it.key }}")
    }
}
