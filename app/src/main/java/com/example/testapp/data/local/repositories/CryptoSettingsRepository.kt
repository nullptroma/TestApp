package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.CryptoDao
import com.example.testapp.data.local.model.DbCryptoSetting
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.cardsettings.CryptoSettings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoSettingsRepository @Inject constructor(
    private val dao: CryptoDao,
    @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) :CardSettingsRepository<CryptoSettings> {
    override suspend fun getById(id: Long): CryptoSettings {
        val jsonList: String
        withContext(_ioDispatcher) {
            val value = dao.getById(id)
            if (value == null) dao.add(DbCryptoSetting(id, Gson().toJson(listOf<String>())))
            jsonList = dao.getById(id)!!.cryptoListJSON
        }
        val itemType = object : TypeToken<List<String>>() {}.type
        return CryptoSettings(Gson().fromJson(jsonList, itemType))
    }

    override suspend fun updateSetting(id: Long, setting: CryptoSettings) {
        withContext(_ioDispatcher) {
            dao.update(DbCryptoSetting(id, Gson().toJson(setting.cryptoIdList)))
        }
    }
}