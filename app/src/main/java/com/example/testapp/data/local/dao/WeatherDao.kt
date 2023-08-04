package com.example.testapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbWeatherSetting

@Dao
interface WeatherDao {
    @Query("SELECT * FROM settings_for_weathers WHERE card_id = :id")
    fun getById(id: Long): LiveData<List<DbWeatherSetting>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(setting: DbWeatherSetting): Long

    @Update
    fun update(setting: DbWeatherSetting)

    @Query("DELETE FROM settings_for_weathers WHERE card_id = :id")
    fun deleteById(id: Long)
}