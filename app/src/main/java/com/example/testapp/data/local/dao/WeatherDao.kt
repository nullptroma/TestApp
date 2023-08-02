package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.data.local.model.DbWeatherSetting

@Dao
interface WeatherDao {
    @Query("SELECT * FROM settings_for_weathers WHERE card_id = :id")
    fun get(id: Int): List<DbWeatherSetting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(setting: DbWeatherSetting)
}