package com.example.testapp.domain.usecases.cards

import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cardsettings.CardSettings
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.domain.models.cards.CardState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class UseCardUseCase<T : CardSettings>(
    private val _useCardSettingsUseCase: UseCardSettingsUseCase<T>,
) {
    abstract val callbackContainer: ICardCallback?
    abstract val state: StateFlow<Map<Long, CardState>>
    protected var settings: Map<Long, T> = mapOf()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            _useCardSettingsUseCase.getFlow().collect {
                settings = it
                onSettingsChange()
            }
        }
    }

    protected fun updateSettingForCard(id: Long, settings: T) {
        if (!this.settings.containsKey(id))
            return
        CoroutineScope(Dispatchers.Default).launch {
            _useCardSettingsUseCase.updateSetting(mapOf(Pair(id, settings)))
        }
    }

    abstract fun createSettingBridge(id: Long): SettingBridge
    protected abstract suspend fun onSettingsChange()
}
