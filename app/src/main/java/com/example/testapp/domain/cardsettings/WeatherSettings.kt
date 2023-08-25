package com.example.testapp.domain.cardsettings

import com.example.testapp.domain.models.CityInfo


data class WeatherSettings(
    var cityInfo: CityInfo = CityInfo()
)
