package com.example.testapp.domain.usecases

import com.example.testapp.data.local.repositories.WeatherSettingsRepository
import com.example.testapp.domain.cardsettings.WeatherSettings
import javax.inject.Singleton

@Singleton
class ChangeWeatherCityUseCase(private val repo: WeatherSettingsRepository) {
    fun setCity(id: Long, city: String) {
        repo.updateSetting(id, WeatherSettings(city))
    }
}