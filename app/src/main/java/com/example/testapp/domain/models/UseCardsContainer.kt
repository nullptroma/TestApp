package com.example.testapp.domain.models

import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.cardsettings.CardSettings
import com.example.testapp.domain.usecases.cards.UseCardUseCase

class UseCardsContainer(val list: List<Pair<UseCardUseCase<CardSettings>, CardType>>)