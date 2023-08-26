package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.MapDao
import com.example.testapp.data.local.model.DbMapSetting
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.MapSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapSettingsRepository @Inject constructor(
    private val dao: MapDao,
    @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) : CardSettingsRepository<MapSettings> {
    override suspend fun getById(id: Long): MapSettings {
        val cityInfo: CityInfo
        withContext(_ioDispatcher) {
            val value = dao.getById(id)
            if (value == null) dao.add(DbMapSetting(id))
            cityInfo = dao.getFull(id)!!.city?.toCityInfo() ?: CityInfo()
        }
        return MapSettings(cityInfo)
    }

    override suspend fun updateSetting(id: Long, setting: MapSettings) {
        withContext(_ioDispatcher) {
            dao.update(DbMapSetting(id, setting.cityInfo.id))
        }
    }
}