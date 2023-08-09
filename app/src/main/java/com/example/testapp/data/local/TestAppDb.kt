package com.example.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbCryptoSetting
import com.example.testapp.data.local.model.DbEnabledCard
import com.example.testapp.data.local.model.DbWeatherSetting


@Database(entities = [DbCryptoSetting::class, DbWeatherSetting::class, DbEnabledCard::class], version = 5, exportSchema = false)
abstract class TestAppDb : RoomDatabase() {
    abstract val weatherDao: WeatherDao
    abstract val enabledCardsDao: EnabledCardsDao
}