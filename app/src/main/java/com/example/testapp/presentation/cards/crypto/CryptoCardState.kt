package com.example.testapp.presentation.cards.crypto

import com.example.testapp.domain.CryptoData

data class CryptoCardState(
    val info: List<CryptoData> = listOf(),
    val loading: Boolean = true,
    val error: Boolean = false,
    val mustBeAnyInfo: Boolean = false
)