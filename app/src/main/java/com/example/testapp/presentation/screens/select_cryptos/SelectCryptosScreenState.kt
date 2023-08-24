package com.example.testapp.presentation.screens.select_cryptos

import com.example.testapp.domain.CryptoData

data class SelectCryptosScreenState(
    val list: List<CryptoData> = listOf(),
    val enabledIds: List<String> = listOf(),
    val showToast:Boolean = false
)
