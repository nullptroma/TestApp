package com.example.testapp.presentation.states.screens

import com.example.testapp.domain.models.CryptoData

data class SelectCryptosScreenState(
    val list: List<CryptoData> = listOf(),
    val enabledIds: List<String> = listOf(),
    val showToast:Boolean = false
)
