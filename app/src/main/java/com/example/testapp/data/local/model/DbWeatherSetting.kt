package com.example.testapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "settings_for_weathers", foreignKeys = [ForeignKey(
        entity = DbEnabledCard::class,
        parentColumns = arrayOf("card_id"),
        childColumns = arrayOf("card_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DbWeatherSetting(
    @PrimaryKey
    @ColumnInfo(name = "card_id")
    val cardId: Long,
    @ColumnInfo(name = "city_id")
    val cityId: Long = 0
)