package com.example.testapp.presentation.states.screens

import com.example.testapp.presentation.states.recycler_view.CryptoItemData

data class SelectCryptosScreenState(
    val list: List<CryptoItemData> = listOf(),
    val showToast:Boolean = false
)
