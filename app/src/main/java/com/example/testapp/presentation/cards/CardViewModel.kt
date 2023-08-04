package com.example.testapp.presentation.cards

import androidx.lifecycle.ViewModel
import com.example.testapp.domain.Cards
import com.example.testapp.presentation.settings.SettingBridge

abstract class CardViewModel: ViewModel() {
    abstract val id: Long
    abstract val cardType: Cards

    abstract fun createSettingBridge() : SettingBridge
}
