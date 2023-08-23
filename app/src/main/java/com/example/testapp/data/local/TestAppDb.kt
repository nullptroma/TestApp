package com.example.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.local.dao.CryptoDao
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.dao.MapDao
import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbCryptoSetting
import com.example.testapp.data.local.model.DbEnabledCard
import com.example.testapp.data.local.model.DbMapSetting
import com.example.testapp.data.local.model.DbWeatherSetting


@Database(entities = [DbCryptoSetting::class, DbWeatherSetting::class, DbEnabledCard::class, DbMapSetting::class], version = 7, exportSchema = false)
abstract class TestAppDb : RoomDatabase() {
    abstract val cryptoDao: CryptoDao
    abstract val weatherDao: WeatherDao
    abstract val mapDao: MapDao
    abstract val enabledCardsDao: EnabledCardsDao
}