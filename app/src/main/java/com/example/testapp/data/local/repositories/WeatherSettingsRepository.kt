package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbWeatherSetting
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.WeatherSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherSettingsRepository @Inject constructor(private val dao: WeatherDao) {
    fun getById(id: Long): WeatherSettings {
        val value = dao.getById(id)
        if(value == null)
            dao.add(DbWeatherSetting(id))
        return WeatherSettings(dao.getFull(id)!!.city?.toCityInfo() ?: CityInfo())
    }

    fun updateSetting(id: Long, setting: WeatherSettings) {
        dao.update(DbWeatherSetting(id, setting.cityInfo.id))
    }
}