package com.example.testapp.presentation.states.screens

import com.example.testapp.domain.models.cards.CardState

data class MainScreenState(val cards: List<Pair<Long, CardState>> = listOf(), val loading: Boolean = true)
