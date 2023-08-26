package com.example.testapp.presentation.cards.crypto

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.MyObserver
import com.example.testapp.data.local.repositories.CryptoSettingsRepository
import com.example.testapp.data.remote.repositories.CryptoRepository
import com.example.testapp.di.IoDispatcher
import com.example.testapp.di.MainDispatcher
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.domain.models.CryptosPackage
import com.example.testapp.domain.models.cardsettings.CryptoSetting
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.presentation.settings.CryptosSettingBridge
import com.example.testapp.presentation.settings.SettingBridge
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoCardViewModel @AssistedInject constructor(
    private val _settingsRepo: CryptoSettingsRepository,
    private val _dataRepo: CryptoRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @Assisted id: Long
) : CardViewModel() {

    override val id: Long
        get() = _id
    override val isSet: State<Boolean>
        get() = _isSet
    private val _isSet = mutableStateOf(false)

    private var _id: Long

    val state: State<CryptoCardState>
        get() = _state
    private val _state = mutableStateOf(CryptoCardState())
    private var _setting: CryptoSetting = CryptoSetting()
    private var _cryptosInfo: CryptosPackage? = null
    private val _observer: MyObserver<CryptosPackage> = MyObserver { pack ->
        _cryptosInfo = pack
        refreshState()
    }

    init {
        _id = id
        _dataRepo.liveData.observeForever(_observer)
        loadSetting()
    }

    private fun refreshState() {
        val pack = _cryptosInfo ?: return
        _state.value = CryptoCardState(
            info = pack.data.filter { _setting.cryptoIdList.contains(it.id) },
            loading = pack.loading,
            error = pack.error,
            mustBeAnyInfo = _setting.cryptoIdList.isNotEmpty()
        )
    }

    private fun loadSetting() {
        viewModelScope.launch(ioDispatcher) {
            _setting = _settingsRepo.getById(_id).copy()
            withContext(mainDispatcher) {
                refreshState()
                _isSet.value = _setting.cryptoIdList.isNotEmpty()
            }
        }
    }

    private fun saveSetting() {
        viewModelScope.launch(ioDispatcher) {
            _settingsRepo.updateSetting(_id, _setting)
            loadSetting()
        }
    }

    private fun setCryptos(list: List<String>) {
        _setting.cryptoIdList = list
        saveSetting()
    }

    override fun createSettingBridge(): SettingBridge {
        return CryptosSettingBridge(_setting.cryptoIdList.toMutableList()) { list ->
            setCryptos(list)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): CryptoCardViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory, // this is the Factory interface
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _dataRepo.liveData.removeObserver(_observer)
    }
}

@Composable
fun cryptoCardViewModel(id: Long): CryptoCardViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, ViewModelFactoryProvider::class.java
    ).cryptoCardViewModelFactory()

    return viewModel(
        factory = CryptoCardViewModel.provideFactory(factory, id), key = id.toString()
    )
}