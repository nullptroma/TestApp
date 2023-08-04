package com.example.testapp.presentation.settings

import com.example.testapp.presentation.Route

abstract class SettingBridge {
    abstract val route:Route
    abstract fun invokeCallback()
}