package com.example.testapp.data.local.repositories.cardsettings

import com.example.testapp.data.local.dao.CryptoDao
import com.example.testapp.data.local.model.DbCryptoSetting
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.cardsettings.CryptoSettings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoSettingsRepository @Inject constructor(
    private val _dao: CryptoDao,
    @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) : CardSettingsRepository<CryptoSettings> {
    override suspend fun checkIds(ids: List<Long>) {
        withContext(_ioDispatcher) {
            val cur = _dao.getAll().map { it.cardId }.toSet()
            val toAdd = mutableListOf<DbCryptoSetting>()
            for (id in ids) if (!cur.contains(id)) toAdd.add(
                DbCryptoSetting(
                    id,
                    Gson().toJson(listOf<String>())
                )
            )
            _dao.add(toAdd)
        }
    }

    override suspend fun getForIds(): Flow<Map<Long, CryptoSettings>> {
        return _dao.getFlow().map { it.toCryptoSettingsMap() }
    }

    override suspend fun setSettings(settings: Map<Long, CryptoSettings>) {
        withContext(_ioDispatcher) {
            _dao.update(settings.let {
                it.map { pair ->
                    DbCryptoSetting(
                        pair.key, Gson().toJson(pair.value.cryptoIdList)
                    )
                }
            })
        }
    }

    private fun List<DbCryptoSetting>.toCryptoSettingsMap(): Map<Long, CryptoSettings> {
        return this.associate { Pair(it.cardId, jsonToCryptoSettings(it.cryptoListJSON)) }
    }


    private val itemType = object : TypeToken<List<String>>() {}.type
    private fun jsonToCryptoSettings(jsonList: String): CryptoSettings {
        return CryptoSettings(Gson().fromJson(jsonList, itemType))
    }
}