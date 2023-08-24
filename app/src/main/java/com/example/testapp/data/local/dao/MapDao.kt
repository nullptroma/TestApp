package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbFullMap
import com.example.testapp.data.local.model.DbMapSetting

@Dao
interface MapDao {
    @Query("SELECT * FROM settings_for_maps WHERE card_id = :id")
    fun getById(id: Long): DbMapSetting?

    @Query("SELECT * FROM settings_for_maps WHERE card_id = :id")
    fun getFull(id:Long): DbFullMap?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(setting: DbMapSetting)

    @Update
    fun update(setting: DbMapSetting)
}