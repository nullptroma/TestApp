package com.example.testapp.data.local.repositories.cardsettings

import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbFullWeather
import com.example.testapp.data.local.model.DbWeatherSetting
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.cardsettings.WeatherSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherSettingsRepository @Inject constructor(
    private val dao: WeatherDao, @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) : CardSettingsRepository<WeatherSettings> {
    override suspend fun checkIds(ids: List<Long>) {
        withContext(_ioDispatcher) {
            val cur = dao.getAll().map { it.cardId }.toSet()
            val toAdd = mutableListOf<DbWeatherSetting>()
            for (id in ids)
                if (!cur.contains(id))
                    toAdd.add(DbWeatherSetting(id))
            dao.add(toAdd)
        }
    }

    override suspend fun getForIds(): Flow<Map<Long, WeatherSettings>> {
        return dao.getFlow().map { it.toWeatherSettingsMap() }
    }

    override suspend fun setSettings(settings: Map<Long, WeatherSettings>) {
        withContext(_ioDispatcher) {
            dao.update(settings.let {
                it.map { pair ->
                    DbWeatherSetting(
                        pair.key,
                        pair.value.cityInfo.id
                    )
                }
            })
        }
    }

    private fun List<DbFullWeather>.toWeatherSettingsMap(): Map<Long, WeatherSettings> {
        return this.associate {
            Pair(
                it.setting.cardId,
                WeatherSettings(it.city?.toCityInfo() ?: CityInfo())
            )
        }
    }
}