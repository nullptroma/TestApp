package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.CryptoDao
import com.example.testapp.data.local.model.DbCryptoSetting
import com.example.testapp.domain.cardsettings.CryptoSetting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoSettingsRepository @Inject constructor(private val dao: CryptoDao) {
    fun getById(id: Long): CryptoSetting {
        val value = dao.getById(id)
        if (value == null) dao.add(DbCryptoSetting(id, Gson().toJson(listOf<String>())))
        val itemType = object : TypeToken<List<String>>() {}.type
        return CryptoSetting(Gson().fromJson(dao.getById(id)!!.cryptoListJSON, itemType))
    }

    fun updateSetting(id: Long, setting: CryptoSetting) {
        dao.update(DbCryptoSetting(id, Gson().toJson(setting.cryptoIdList)))
    }
}