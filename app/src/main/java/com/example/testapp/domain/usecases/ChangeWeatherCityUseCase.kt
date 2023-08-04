package com.example.testapp.domain.usecases

import com.example.testapp.data.local.repositories.WeatherSettingsRepository
import com.example.testapp.domain.cardsettings.WeatherSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangeWeatherCityUseCase @Inject constructor(private val repo: WeatherSettingsRepository) {
    fun setCity(id: Long, city: String): Boolean {
        return repo.updateSetting(id, WeatherSettings(city))
    }
}