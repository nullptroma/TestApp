package com.example.testapp.domain.models.cardsettings

import com.example.testapp.domain.CardType

data class EnabledCard(val id: Long, val type: CardType, var priority: Long=100)