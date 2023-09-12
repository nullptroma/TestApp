package com.example.testapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.UseCardsContainer
import com.example.testapp.domain.models.cards.CardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.GetFlowEnabledCardsUseCase
import com.example.testapp.presentation.states.screens.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getFlowEnabledCardsUseCase: GetFlowEnabledCardsUseCase, useCards: UseCardsContainer
) : ViewModel() {
    val callbackMap: Map<CardType, ICardCallback?>
    val state: StateFlow<MainScreenState>
        get() = _state
    private val _state = MutableStateFlow(MainScreenState())
    private var _enabledCards = listOf<Long>()
    private var _cardStates: Map<Long, CardState> = mapOf()
    private val _settingBridgeFactoryMap: Map<CardType, (Long) -> SettingBridge>

    init {
        callbackMap = useCards.list.associate { it.second to it.first.callbackContainer }
        _settingBridgeFactoryMap =
            useCards.list.associate { it.second to { id -> it.first.createSettingBridge(id) } }

        var flow: StateFlow<Map<Long, CardState>> = MutableStateFlow(mapOf())
        for (state in useCards.list.map { it.first.state }) {
            flow = combineState(flow, state) { l, r ->
                l + r
            }
        }

        viewModelScope.launch {
            launch {
                getFlowEnabledCardsUseCase.flowData.collect {
                    _enabledCards = it.sortedBy { card -> card.priority }.map { card -> card.id }
                    refresh()
                }
            }
            launch {
                flow.collect {
                    _cardStates = it
                    refresh()
                }
            }
        }
    }

    fun createSettingBridge(id: Long): SettingBridge? {
        return _settingBridgeFactoryMap[_cardStates[id]?.type]?.invoke(id)
    }

    private fun refresh() {
        val list = mutableListOf<Pair<Long, CardState>>()
        for (card in _enabledCards) {
            val state = _cardStates[card] ?: continue
            list.add(card to state)
        }
        _state.value = MainScreenState(list, false)
    }

    private fun <T1, T2, R> combineState(
        flow1: StateFlow<T1>,
        flow2: StateFlow<T2>,
        sharingStarted: SharingStarted = SharingStarted.Eagerly,
        transform: (T1, T2) -> R
    ): StateFlow<R> = combine(flow1, flow2) { o1, o2 ->
        transform.invoke(o1, o2)
    }.stateIn(
        CoroutineScope(Dispatchers.Default),
        sharingStarted,
        transform.invoke(flow1.value, flow2.value)
    )
}