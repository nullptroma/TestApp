package com.example.testapp.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class DbFullMap(
    @Embedded val setting: DbWeatherSetting,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: DbCity?
)
