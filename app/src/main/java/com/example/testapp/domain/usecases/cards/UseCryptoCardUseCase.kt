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

    init {
        CoroutineScope(Dispatchers.Default).launch {
            getFlowCryptoPackageUseCase.flowData.collect {
                //Log.d("MyTag", "Cryptos pakage: ${it.data.size}, ${it.loading}")
            }
        }
    }

    private fun setCryptos(id:Long, list: List<String>) {
        if (!settings.containsKey(id))
            return
        updateSettingForCard(id, settings[id]!!.copy(cryptoIdList = list))
    }

    override fun createSettingBridge(id:Long): SettingBridge {
        return CryptosSettingBridge(settings[id]!!.cryptoIdList.toMutableList()) { list ->
            setCryptos(id, list)
        }
    }

    override suspend fun onSettingsChange() {
        //Log.d("MyTag", "Crypto sets: ${settings.map { it.key }}")
        checkEntries()
    }

    private fun checkEntries() {
        val add = settings.filter { pair -> !state.value.containsKey(pair.key) }
        _state.value = _state.value.toMutableMap().apply {
            this+=add.map { it.key to  CryptoCardState(
                it.key,
                needSettings = it.value.cryptoIdList.isEmpty()
            )}
        }.filter { pair -> settings.containsKey(pair.key) }
    }
}
