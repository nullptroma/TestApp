package com.example.testapp.domain.models.cards

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.Weather

data class WeatherCardState(
    override val id: Long,
    val data: Weather? = null,
    val error: Boolean = false,
    override val needSettings: Boolean = false
) : CardState() {
    override val type: CardType
        get() = CardType.WEATHER
}