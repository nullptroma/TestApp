package com.example.testapp.presentation.screens.menu_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.MyObserver
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.cardsettings.EnabledCard
import com.example.testapp.domain.usecases.GetLiveEnabledCardsUseCase
import com.example.testapp.domain.usecases.SetEnabledCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuSettingsViewModel @Inject constructor(
    private val getLiveEnabledCardsUseCase: GetLiveEnabledCardsUseCase,
    private val setEnabledCardsUseCase: SetEnabledCardsUseCase
) :
    ViewModel() {
    val state: State<MenuSettingsScreenState>
        get() = _state
    private val _state = mutableStateOf(MenuSettingsScreenState())
    private lateinit var mutableList: MutableList<EnabledCard>
    private val observer: Observer<List<EnabledCard>>
    private var _nextId = -1L

    init {
        observer = MyObserver { value ->
            refresh(value)
        }
        getLiveEnabledCardsUseCase.liveData.observeForever(observer)
    }

    private fun refresh(orig:List<EnabledCard>) {
        drawList(orig)
        _nextId = -1L
    }

    private fun drawList(orig: List<EnabledCard>) {
        mutableList = mutableListOf()
        mutableList.addAll(orig)
        _state.value = _state.value.copy(list = mutableList.toMutableList())
    }

    fun save() {
        for (i in 0 until mutableList.size) {
            mutableList[i] = mutableList[i].copy(id = maxOf(0L, mutableList[i].id), priority = i.toLong())
        }

        viewModelScope.launch {
            setEnabledCardsUseCase.setAll(mutableList)
        }
    }

    fun move(from: Int, to: Int) {
        val buf = mutableList[from]
        mutableList[from] = mutableList[to]
        mutableList[to] = buf

        _state.value = _state.value.copy(list = mutableList.toMutableList())
    }


    fun createCard(type: CardType) {
        mutableList.add(EnabledCard(_nextId--, type, 100))
        _state.value = _state.value.copy(list = mutableList.toMutableList())
    }

    fun removeCard(id: Long) {
        mutableList.removeIf {
            it.id == id
        }
        _state.value = _state.value.copy(list = mutableList.toMutableList())
    }

    override fun onCleared() {
        super.onCleared()
        getLiveEnabledCardsUseCase.liveData.removeObserver(observer)
    }
}

