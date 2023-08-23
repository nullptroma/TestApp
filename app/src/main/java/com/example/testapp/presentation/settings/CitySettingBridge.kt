package com.example.testapp.presentation.settings

class CitySettingBridge constructor(
    var city: String = "",
    private val callback:(String)->Unit
) : SettingBridge() {
    override fun invokeCallback() {
        callback(city)
    }
}
