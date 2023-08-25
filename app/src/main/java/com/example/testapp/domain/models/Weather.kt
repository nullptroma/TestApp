package com.example.testapp.domain.models

data class Weather(
    val city: String,
    val iconUrl: String,
    val windSpeed: Double,
    val windDeg: Int,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val desc:String,
    val temp:Double,
    val tempFeels:Double
)
