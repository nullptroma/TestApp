package com.example.testapp.presentation.states.cards

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.Coordinates

data class MapCardState(val coordinates: Coordinates = Coordinates()): CardState(CardType.MAP)