package com.example.testapp.data.local.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbWeatherSetting
import com.example.testapp.domain.cardsettings.WeatherSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherSettingsRepository @Inject constructor(private val dao: WeatherDao) {
    fun getById(id: Long): LiveData<WeatherSettings?> {
        return dao.getById(id).map { it.firstOrNull()?.let { set -> WeatherSettings(set.city) } }
    }

    fun updateSetting(id: Long, setting: WeatherSettings) {
        dao.update(DbWeatherSetting(id, setting.city))
    }

    fun addSetting(setting: WeatherSettings): Long {
        return dao.add(DbWeatherSetting(0, setting.city))
    }
}