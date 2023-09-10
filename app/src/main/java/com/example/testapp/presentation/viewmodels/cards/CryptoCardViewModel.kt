package com.example.testapp.presentation.viewmodels.cards

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.di.ViewModelFactoryProvider
import com.example.testapp.domain.models.CryptosPackage
import com.example.testapp.domain.models.cardsettings.CryptoSettings
import com.example.testapp.domain.models.settings.CryptosSettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.cardsdata.GetLiveCryptoPackageUseCase
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.presentation.cards.CardViewModel
import com.example.testapp.presentation.states.cards.CryptoCardState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CryptoCardViewModel @AssistedInject constructor(
    private val useCryptoSettingsUseCase: UseCardSettingsUseCase<CryptoSettings>,
    private val _getLiveCryptoPackageUseCase: GetLiveCryptoPackageUseCase,
    @Assisted id: Long
) : CardViewModel() {

    override val id: Long
        get() = _id

    private var _id: Long

    override val state: StateFlow<CryptoCardState>
        get() = _state
    private val _state = MutableStateFlow(CryptoCardState())
    private var _setting: CryptoSettings = CryptoSettings()
    private var _cryptosInfo: CryptosPackage? = null

    init {
        _id = id
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
        viewModelScope.launch {
            _setting = useCryptoSettingsUseCase.read(_id).copy()
            refreshState()
            //_isSet.value = _setting.cryptoIdList.isNotEmpty()
        }
    }

    private fun saveSetting() {
        viewModelScope.launch {
            useCryptoSettingsUseCase.updateSetting(_id, _setting)
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