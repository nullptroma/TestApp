package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbFullWeather
import com.example.testapp.data.local.model.DbWeatherSetting
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM settings_for_weathers")
    fun getAll(): List<DbWeatherSetting>

    @Query("SELECT * FROM settings_for_weathers")
    fun getFlow(): Flow<List<DbFullWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(settings: List<DbWeatherSetting>)

    @Update
    fun update(settings: List<DbWeatherSetting>)
}