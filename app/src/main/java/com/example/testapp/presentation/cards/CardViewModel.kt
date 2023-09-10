package com.example.testapp.presentation.cards

import androidx.lifecycle.ViewModel
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.presentation.states.cards.CardState
import kotlinx.coroutines.flow.StateFlow

abstract class CardViewModel : ViewModel() {
    abstract val id: Long
    abstract val state: StateFlow<CardState>

    abstract fun createSettingBridge(): SettingBridge
}
