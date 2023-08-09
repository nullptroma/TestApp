package com.example.testapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_for_cryptos")
data class DbCryptoSetting(
    @PrimaryKey
    @ColumnInfo(name = "card_id")
    val cardId: Long
)