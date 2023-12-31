package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbFullWeather
import com.example.testapp.data.local.model.DbWeatherSetting

@Dao
interface WeatherDao {
    @Query("SELECT * FROM settings_for_weathers WHERE card_id = :id")
    fun getById(id: Long): DbWeatherSetting?

    @Query("SELECT * FROM settings_for_weathers WHERE card_id = :id")
    fun getFull(id:Long):DbFullWeather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(setting: DbWeatherSetting)

    @Update
    fun update(setting: DbWeatherSetting)
}