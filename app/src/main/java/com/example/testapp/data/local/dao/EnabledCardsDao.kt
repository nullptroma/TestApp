package com.example.testapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbEnabledCard

@Dao
interface EnabledCardsDao {
    @Query("SELECT * FROM enabled_cards ORDER BY priority")
    fun getAll(): LiveData<List<DbEnabledCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(list: List<DbEnabledCard>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: DbEnabledCard)

    @Update
    fun updateAll(list: List<DbEnabledCard>)

    @Query("DELETE FROM enabled_cards WHERE card_id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM enabled_cards")
    fun deleteAll()
}