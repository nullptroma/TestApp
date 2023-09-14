package com.example.testapp.presentation

import com.example.testapp.R
import com.example.testapp.domain.CardType

object SettingsTable {
    val table = mapOf(
        CardType.WEATHER to R.id.action_Menu_to_selectCityFragment,
        CardType.MAP to R.id.action_Menu_to_selectCityFragment,
        CardType.CRYPTO to R.id.action_Menu_to_selectCryptosFragment,
    )
}