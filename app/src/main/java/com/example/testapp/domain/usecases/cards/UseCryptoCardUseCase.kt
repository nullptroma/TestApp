package com.example.testapp.domain.usecases.cards

import com.example.testapp.domain.models.cards.CryptoCardState
import com.example.testapp.domain.models.cards_callbacks.ICardCallback
import com.example.testapp.domain.models.cardsettings.CryptoSettings
import com.example.testapp.domain.models.settings.CryptosSettingBridge
import com.example.testapp.domain.models.settings.SettingBridge
import com.example.testapp.domain.usecases.cardsdata.GetFlowCryptoPackageUseCase
import com.example.testapp.domain.usecases.get_settings.UseCardSettingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UseCryptoCardUseCase @Inject constructor(
    useCryptoSettingsUseCase: UseCardSettingsUseCase<CryptoSettings>,
    getFlowCryptoPackageUseCase: GetFlowCryptoPackageUseCase
) : UseCardUseCase<CryptoSettings>(useCryptoSettingsUseCase) {
    override val callbackContainer: ICardCallback? = null
    override val state: StateFlow<Map<Long, CryptoCardState>>
        get() = _state
    private val _state = MutableStateFlow(mapOf<Long, CryptoCardState>())
    private val _package = getFlowCryptoPackageUseCase.flowData

    init {
        CoroutineScope(Dispatchers.Default).launch {
            _package.collect {
                refreshState()
            }
        }
    }

    private fun refreshState() {
        val map = mutableMapOf<Long, CryptoCardState>()
        for (set in settings) {
            val list = _package.value.data.filter { set.value.cryptoIdList.contains(it.id) }
            map[set.key] = CryptoCardState(
                id = set.key,
                info = list,
                error = _package.value.error,
                loading = _package.value.loading,
                needSettings = set.value.cryptoIdList.isEmpty()
            )
        }
        _state.value = map
    }

    private fun setCryptos(id: Long, list: List<String>) {
        if (!settings.containsKey(id))
            return
        updateSettingForCard(id, settings[id]!!.copy(cryptoIdList = list))
    }

    override fun createSettingBridge(id: Long): SettingBridge {
        return CryptosSettingBridge(settings[id]!!.cryptoIdList.toMutableList()) { list ->
            setCryptos(id, list)
        }
    }

    override suspend fun onSettingsChange() {
        refreshState()
    }
}
