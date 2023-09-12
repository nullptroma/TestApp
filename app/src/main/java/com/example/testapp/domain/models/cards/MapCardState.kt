package com.example.testapp.domain.models.cards

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.city.Coordinates

data class MapCardState(
    override val id: Long,
    val coordinates: Coordinates = Coordinates(),
    override val needSettings: Boolean = false
) : CardState() {
    override val type: CardType
        get() = CardType.MAP
}