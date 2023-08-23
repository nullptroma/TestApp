package com.example.testapp.presentation.settings

class CryptosSettingBridge(
    var enabledCryptos: MutableList<String>,
    private val callback: (List<String>) -> Unit
) : SettingBridge() {
    override fun invokeCallback() {
        callback(enabledCryptos)
    }
}
