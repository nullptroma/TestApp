package com.example.testapp.presentation.states.cards

import com.example.testapp.domain.CardType

sealed class CardState(val type: CardType, val needSetting: Boolean = true)
