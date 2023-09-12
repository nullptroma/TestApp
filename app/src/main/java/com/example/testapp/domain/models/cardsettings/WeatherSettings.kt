package com.example.testapp.domain.models.cardsettings

import com.example.testapp.domain.models.city.CityInfo


data class WeatherSettings(
    var cityInfo: CityInfo = CityInfo()
): CardSettings()
