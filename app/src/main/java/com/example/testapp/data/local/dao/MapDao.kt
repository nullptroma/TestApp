package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbFullMap
import com.example.testapp.data.local.model.DbMapSetting
import kotlinx.coroutines.flow.Flow

@Dao
interface MapDao {
    @Query("SELECT * FROM settings_for_maps")
    fun getAll(): List<DbMapSetting>

    @Query("SELECT * FROM settings_for_maps")
    fun getFlow(): Flow<List<DbFullMap>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(settings: List<DbMapSetting>)

    @Update
    fun update(settings: List<DbMapSetting>)
}