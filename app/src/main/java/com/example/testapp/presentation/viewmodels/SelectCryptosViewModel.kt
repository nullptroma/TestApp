package com.example.testapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.models.crypto.CryptoData
import com.example.testapp.domain.models.settings.CryptosSettingBridge
import com.example.testapp.domain.models.settings.SettingBridgeContainer
import com.example.testapp.domain.usecases.GetFirstValidCryptoPackageUseCase
import com.example.testapp.presentation.states.recycler_view.CryptoItemData
import com.example.testapp.presentation.states.screens.SelectCryptosScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class SelectCryptosViewModel @Inject constructor(
    private val useCase: GetFirstValidCryptoPackageUseCase,
    private val _bridgeContainer: SettingBridgeContainer
) : ViewModel() {
    val state: StateFlow<SelectCryptosScreenState>
        get() {
            refreshState()
            return _state
        }
    private val _state = MutableStateFlow(SelectCryptosScreenState())
    private var origList: List<CryptoData> = listOf()
    private val _bridge
        get() = _bridgeContainer.bridge as? CryptosSettingBridge

    init {
        viewModelScope.launch {
            origList = useCase.get().data
            refreshState()
        }
    }

    private fun refreshState() {
        val bridge = _bridge ?: return
        val sortedList = origList.sortedWith { l, r ->
            if (bridge.enabledCryptos.contains(l.id)) {
                if (bridge.enabledCryptos.contains(r.id)) return@sortedWith if (bridge.enabledCryptos.indexOf(
                        l.id
                    ) < bridge.enabledCryptos.indexOf(
                        r.id
                    )
                ) -1 else 1
                else return@sortedWith -1
            } else if (bridge.enabledCryptos.contains(r.id)) return@sortedWith 1
            else return@sortedWith if (l.id < r.id) -1 else 1
        }
        val enabledIds = bridge.enabledCryptos.toImmutableList()
        _state.value = _state.value.copy(list = sortedList.map {
            CryptoItemData(
                it.id, it.imageUrl, it.name, enabledIds.contains(it.id)
            )
        })
    }

    fun showToast(bool: Boolean) {
        _state.value = _state.value.copy(showToast = bool)
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
