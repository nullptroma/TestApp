package com.example.testapp.presentation.screens.menusettings

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.MyObserver
import com.example.testapp.data.local.repositories.EnabledCardsRepository
import com.example.testapp.di.IoDispatcher
import com.example.testapp.di.MainDispatcher
import com.example.testapp.domain.Cards
import com.example.testapp.domain.EnabledCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MenuSettingsViewModel @Inject constructor(
    private val repo: EnabledCardsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) :
    ViewModel() {
    val state: State<MenuSettingsScreenState>
        get() = _state
    private val _state = mutableStateOf(MenuSettingsScreenState())
    private lateinit var mutableList: MutableList<EnabledCard>
    private val liveData: LiveData<List<EnabledCard>> = repo.getAll()
    private val observer: Observer<List<EnabledCard>>

    init {
        observer = MyObserver { value ->
            refresh()
            Log.d("MyTag", "Hello!")
        }
        liveData.observeForever(observer)
    }

    fun refresh() {
        Log.d("MyTag", "Refresh")
        viewModelScope.launch(ioDispatcher) {
            val orig = liveData.value ?: return@launch
            withContext(mainDispatcher) {
                drawList(orig)
            }
        }
    }

    private fun drawList(orig: List<EnabledCard>) {
        mutableList = mutableListOf()
        mutableList.addAll(orig)
        _state.value = _state.value.copy(list = mutableList.toMutableList())
    }

    fun save() {
        for (i in 0 until mutableList.size) {
            mutableList[i].priority = i.toLong()
        }

        viewModelScope.launch(ioDispatcher) {
            repo.setAll(mutableList)
        }
    }

    fun move(from: Int, to: Int) {
        val buf = mutableList[from]
        mutableList[from] = mutableList[to]
        mutableList[to] = buf

        _state.value = _state.value.copy(list = mutableList.toMutableList()) // работает
    }

    fun createCard(type: Cards) {
        mutableList.add(EnabledCard(0, type, 100))
        _state.value = _state.value.copy(list = mutableList.toMutableList()) // работает
    }

    fun removeCard(id: Long) {
        mutableList.removeIf {
            it.id == id
        }
        _state.value = _state.value.copy(list = mutableList.toMutableList()) // работает
    }

    override fun onCleared() {
        super.onCleared()
        liveData.removeObserver(observer)
    }
}

