package com.example.testapp.domain.usecases.cards

import android.util.Log
import com.example.testapp.domain.models.cards.CardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cardsettings.CardSettings
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
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
    private var _lastChangedId: Long = -1L


    init {
        CoroutineScope(Dispatchers.Default).launch {
            _useCardSettingsUseCase.getFlow().collect {
                settings = it
                Log.d("MyTag", "Settings: $_lastChangedId")
                if (_lastChangedId == -1L) {
                    onSettingsChange()
                } else {
                    val id = _lastChangedId
                    _lastChangedId = -1L
                    onSettingsChange(id)
                }
            }
        }
    }

    protected fun updateSettingForCard(id: Long, settings: T) {
        if (!this.settings.containsKey(id))
            return
        CoroutineScope(Dispatchers.Default).launch {
            _lastChangedId = id
            _useCardSettingsUseCase.updateSetting(mapOf(Pair(id, settings)))
        }
    }

    abstract fun createSettingBridge(id: Long): SettingBridge
    protected abstract suspend fun onSettingsChange()
    protected abstract suspend fun onSettingsChange(id: Long)
}
