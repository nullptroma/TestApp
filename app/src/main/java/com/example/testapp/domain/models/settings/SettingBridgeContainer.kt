package com.example.testapp.domain.models.settings

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingBridgeContainer @Inject constructor() {
    var bridge: SettingBridge? = null
}