package com.example.testapp.domain.usecases.cards

import android.util.Log
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.cardsettings.CryptoSettings
import com.example.testapp.domain.models.settings.CryptosSettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.cardsdata.GetFlowCryptoPackageUseCase
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import com.example.testapp.presentation.states.cards.CryptoCardState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseCryptoCardUseCase @Inject constructor(
    useCryptoSettingsUseCase: UseCardSettingsUseCase<CryptoSettings>,
    private val _getFlowCryptoPackageUseCase: GetFlowCryptoPackageUseCase,
    @IoDispatcher private val _ioDispatcher : CoroutineDispatcher
) : UseCardUseCase<CryptoSettings>(useCryptoSettingsUseCase, _ioDispatcher) {
    override val state: StateFlow<Map<Long, CryptoCardState>>
        get() = _state
    private val _state = MutableStateFlow(mapOf<Long, CryptoCardState>())


    init {
        loadSetting()
    }

    private fun refreshState() {
    }

    private fun loadSetting() {
    }

    private fun saveSetting() {
    }

    private fun setCryptos(list: List<String>) {
    }

    override fun createSettingBridge(id:Long): SettingBridge {
        return CryptosSettingBridge(settings[id]!!.cryptoIdList.toMutableList()) { list ->
            setCryptos(list)
        }
    }

    override fun onSettingsChange() {
        Log.d("MyTag", "Crypto sets: ${settings.map { it.key }}")
    }
}
