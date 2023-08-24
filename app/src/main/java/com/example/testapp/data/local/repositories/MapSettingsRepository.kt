package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.MapDao
import com.example.testapp.data.local.model.DbMapSetting
import com.example.testapp.domain.CityInfo
import com.example.testapp.domain.cardsettings.MapSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapSettingsRepository @Inject constructor(private val dao: MapDao) {
    fun getById(id: Long): MapSettings {
        val value = dao.getById(id)
        if (value == null) dao.add(DbMapSetting(id))
        return MapSettings(dao.getFull(id)!!.city?.toCityInfo() ?: CityInfo())
    }

    fun updateSetting(id: Long, setting: MapSettings) {
        dao.update(DbMapSetting(id, setting.cityInfo.id))
    }
}