package com.example.testapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.models.CryptoData
import com.example.testapp.domain.usecases.GetFirstValidCryptoPackageUseCase
import com.example.testapp.domain.models.settings.CryptosSettingBridge
import com.example.testapp.presentation.states.screens.SelectCryptosScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class SelectCryptosViewModel @Inject constructor(private val useCase: GetFirstValidCryptoPackageUseCase) :
    ViewModel() {
    val state: State<SelectCryptosScreenState>
        get() = _state
    private val _state = mutableStateOf(SelectCryptosScreenState())
    private var origList: List<CryptoData> = listOf()
    private var _bridge: CryptosSettingBridge? = null

    init {
        viewModelScope.launch {
            origList = useCase.get().data
            refreshState()
        }
    }

    private fun refreshState() {
        val bridge = _bridge ?: return
        _state.value = _state.value.copy(list = origList.sortedWith { l, r ->
            if (bridge.enabledCryptos.contains(l.id)) {
                if (bridge.enabledCryptos.contains(r.id)) return@sortedWith if (bridge.enabledCryptos.indexOf(l.id) < bridge.enabledCryptos.indexOf(
                        r.id
                    )
                ) -1 else 1
                else return@sortedWith -1
            } else if (bridge.enabledCryptos.contains(r.id)) return@sortedWith 1
            else return@sortedWith if (l.id < r.id) -1 else 1
        }, enabledIds = bridge.enabledCryptos.toImmutableList())
    }

    fun showToast(bool:Boolean) {
        _state.value = _state.value.copy(showToast = bool)
    }

    fun setBridge(bridge: CryptosSettingBridge) {
        _bridge = bridge
        refreshState()
    }

    fun save() {
        _bridge?.invokeCallback()
    }

    fun changeIdSelect(id: String) {
        val bridge = _bridge ?: return
        val cur = bridge.enabledCryptos
        if (cur.contains(id)) cur.remove(id)
        else {
            cur.add(id)
            if (cur.size > 3) {
                cur.removeAt(0)
                showToast(true)
            }
        }
        refreshState()
    }
}
