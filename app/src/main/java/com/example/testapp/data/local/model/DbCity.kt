package com.example.testapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.domain.models.CityInfo
import com.example.testapp.domain.models.Coordinates

@Entity(tableName = "cities")
data class DbCity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toCityInfo(): CityInfo {
        return CityInfo(id, name, country, Coordinates(latitude, longitude))
    }
}
