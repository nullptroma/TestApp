package com.example.testapp.domain.models.settings

import com.example.testapp.domain.models.city.CityInfo

class CitySettingBridge constructor(
    var city: CityInfo = CityInfo(),
    private val callback:(CityInfo)->Unit
) : SettingBridge() {
    override fun invokeCallback() {
        callback(city)
    }
}
