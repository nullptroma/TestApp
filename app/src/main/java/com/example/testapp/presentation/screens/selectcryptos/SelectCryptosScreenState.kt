package com.example.testapp.presentation.screens.selectcryptos

import com.example.testapp.domain.CryptoData

data class SelectCryptosScreenState(
    val list: List<CryptoData> = listOf(),
    val enabledIds: List<String> = listOf(),
    val showToast:Boolean = false
)
