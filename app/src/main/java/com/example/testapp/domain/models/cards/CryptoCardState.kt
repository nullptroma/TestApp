package com.example.testapp.domain.models.cards

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.crypto.CryptoData

data class CryptoCardState(
    override val id: Long,
    val info: List<CryptoData> = listOf(),
    val loading: Boolean = false,
    val error: Boolean = false,
    override val needSettings: Boolean = false
) : CardState() {
    override val type: CardType
        get() = CardType.CRYPTO
}