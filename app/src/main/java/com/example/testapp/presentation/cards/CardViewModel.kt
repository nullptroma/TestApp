package com.example.testapp.presentation.cards

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.testapp.domain.models.settings.SettingBridge

abstract class CardViewModel : ViewModel() {
    abstract val id: Long
    abstract val isSet: State<Boolean>

    abstract fun createSettingBridge(): SettingBridge
}
