package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbWeatherSetting
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.WeatherSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherSettingsRepository @Inject constructor(
    private val dao: WeatherDao, @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) : CardSettingsRepository<WeatherSettings> {
    override suspend fun getById(id: Long): WeatherSettings {
        val cityInfo: CityInfo
        withContext(_ioDispatcher) {
            val value = dao.getById(id)
            if (value == null) dao.add(DbWeatherSetting(id))
            cityInfo = dao.getFull(id)!!.city?.toCityInfo() ?: CityInfo()
        }
        return WeatherSettings(cityInfo)
    }

    override suspend fun updateSetting(id: Long, setting: WeatherSettings) {
        withContext(_ioDispatcher) {
            dao.update(DbWeatherSetting(id, setting.cityInfo.id))
        }
    }
}