package com.example.testapp.domain.usecases.cards

import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.cardsettings.CardSettings
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.presentation.states.cards.CardState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn

abstract class UseCardUseCase<T : CardSettings>(private val _useCardSettingsUseCase: UseCardSettingsUseCase<T>,
                                                @IoDispatcher private val _ioDispatcher : CoroutineDispatcher
) {
    abstract val state: StateFlow<Map<Long, CardState>>
    protected var settings: Map<Long, T> = mapOf()

    init {
        flow<Unit> {
            _useCardSettingsUseCase.getFlow().collect {
                settings = it
                onSettingsChange()
            }
        }.launchIn(CoroutineScope(_ioDispatcher))
    }

    abstract fun createSettingBridge(id:Long): SettingBridge
    protected abstract fun onSettingsChange()
}
