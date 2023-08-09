package com.example.testapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.domain.Cards

@Entity(tableName = "enabled_cards")
data class DbEnabledCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "card_id")
    val cardId: Long = 0,
    val cardType: Cards,
    val priority: Long
)
