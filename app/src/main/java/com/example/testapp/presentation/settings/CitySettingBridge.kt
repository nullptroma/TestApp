package com.example.testapp.presentation.settings

import com.example.testapp.domain.models.CityInfo

class CitySettingBridge constructor(
    var city: CityInfo = CityInfo(),
    private val callback:(CityInfo)->Unit
) : SettingBridge() {
    override fun invokeCallback() {
        callback(city)
    }
}
