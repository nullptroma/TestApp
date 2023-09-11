package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbCryptoSetting
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {
    @Query("SELECT * FROM settings_for_cryptos")
    fun getAll(): List<DbCryptoSetting>

    @Query("SELECT * FROM settings_for_cryptos")
    fun getFlow(): Flow<List<DbCryptoSetting>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(settings: List<DbCryptoSetting>)

    @Update
    fun update(settings: List<DbCryptoSetting>)
}