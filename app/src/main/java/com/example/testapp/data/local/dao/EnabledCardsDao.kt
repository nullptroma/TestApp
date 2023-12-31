package com.example.testapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbEnabledCard

@Dao
interface EnabledCardsDao {
    @Query("SELECT * FROM enabled_cards ORDER BY priority")
    fun getAllLive(): LiveData<List<DbEnabledCard>>

    @Query("SELECT * FROM enabled_cards ORDER BY priority")
    fun getAll(): List<DbEnabledCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(list: List<DbEnabledCard>)

    @Update
    fun updateAll(list: List<DbEnabledCard>)

    @Delete
    fun deleteAll(list: List<DbEnabledCard>)
}