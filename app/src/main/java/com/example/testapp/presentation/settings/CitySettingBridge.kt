package com.example.testapp.presentation.settings

import com.example.testapp.presentation.Route

class CitySettingBridge constructor(
    var city: String = "",
    private val callback:(String)->Unit
) : SettingBridge() {
    override val route: Route
        get() = Route.SELECT_CITY_SCREEN

    override fun invokeCallback() {
        callback(city)
    }
}
