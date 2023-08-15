package com.example.testapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey


data class DbCardSettingTouch(
    @PrimaryKey
    @ColumnInfo(name = "card_id")
    val cardId: Long
)