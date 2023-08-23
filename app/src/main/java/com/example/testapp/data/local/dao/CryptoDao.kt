package com.example.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.data.local.model.DbCryptoSetting

@Dao
interface CryptoDao {
    @Query("SELECT * FROM settings_for_cryptos WHERE card_id = :id")
    fun getById(id: Long): DbCryptoSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(setting: DbCryptoSetting)

    @Update
    fun update(setting: DbCryptoSetting)
}