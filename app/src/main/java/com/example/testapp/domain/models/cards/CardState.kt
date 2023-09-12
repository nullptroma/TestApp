package com.example.testapp.domain.models.cards

import com.example.testapp.domain.CardType

sealed class CardState {
    abstract val id: Long
    abstract val type: CardType
    abstract val needSettings: Boolean
}
