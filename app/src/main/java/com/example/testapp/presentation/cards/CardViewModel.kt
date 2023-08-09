package com.example.testapp.presentation.cards

import androidx.lifecycle.ViewModel
import com.example.testapp.presentation.settings.SettingBridge

abstract class CardViewModel: ViewModel() {
    abstract val id: Long

    abstract fun createSettingBridge() : SettingBridge
}
