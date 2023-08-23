package com.example.testapp.presentation.screens.selectcryptos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testapp.MyObserver
import com.example.testapp.data.remote.repositories.CryptoRepository
import com.example.testapp.domain.CryptoData
import com.example.testapp.domain.CryptosPackage
import com.example.testapp.presentation.settings.CryptosSettingBridge
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class SelectCryptosViewModel @Inject constructor(private val repository: CryptoRepository) :
    ViewModel() {
    val state: State<SelectCryptosScreenState>
        get() = _state
    private val _state = mutableStateOf(SelectCryptosScreenState())
    private var origList: List<CryptoData> = listOf()
    private val _observer: MyObserver<CryptosPackage> = MyObserver {
        origList = it.data
        if(!it.error && !it.loading)
            removeObserver()
        refreshState()
    }
    private var _bridge: CryptosSettingBridge? = null

    init {
        repository.liveData.observeForever(_observer)
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

    private fun removeObserver() {
        repository.liveData.removeObserver(_observer)
    }
}
