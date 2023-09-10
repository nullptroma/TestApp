package com.example.testapp.presentation.states.cards

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.CryptoData

data class CryptoCardState(
    val info: List<CryptoData> = listOf(),
    val loading: Boolean = true,
    val error: Boolean = false,
    val mustBeAnyInfo: Boolean = false
) : CardState(CardType.CRYPTO)