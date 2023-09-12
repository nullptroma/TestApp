package com.example.testapp.data.local.repositories.cardsettings

import com.example.testapp.data.local.dao.MapDao
import com.example.testapp.data.local.model.DbFullMap
import com.example.testapp.data.local.model.DbMapSetting
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.city.CityInfo
import com.example.testapp.domain.models.cardsettings.MapSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapSettingsRepository @Inject constructor(
    private val _dao: MapDao,
    @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) : CardSettingsRepository<MapSettings> {
    override suspend fun checkIds(ids: List<Long>) {
        withContext(_ioDispatcher) {
            val cur = _dao.getAll().map { it.cardId }.toSet()
            val toAdd = mutableListOf<DbMapSetting>()
            for (id in ids) if (!cur.contains(id)) toAdd.add(DbMapSetting(id))
            _dao.add(toAdd)
        }
    }

    override suspend fun getForIds(): Flow<Map<Long, MapSettings>> {
        return _dao.getFlow().map { it.toMapSettingsMap() }
    }

    override suspend fun setSettings(settings: Map<Long, MapSettings>) {
        withContext(_ioDispatcher) {
            _dao.update(settings.let {
                it.map { pair ->
                    DbMapSetting(
                        pair.key, pair.value.cityInfo.id
                    )
                }
            })
        }
    }

    private fun List<DbFullMap>.toMapSettingsMap(): Map<Long, MapSettings> {
        return this.associate {
            Pair(
                it.setting.cardId,
                MapSettings(it.city?.toCityInfo() ?: CityInfo())
            )
        }
    }
}