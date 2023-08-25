package com.example.testapp.domain.models

import com.example.testapp.domain.CardType

data class EnabledCard(val id: Long, val type: CardType, var priority: Long=100)