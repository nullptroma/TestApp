package com.example.testapp.data.remote.repositories

import com.example.testapp.data.remote.api.CoingeckoApiV3
import com.example.testapp.di.IoDispatcher
import com.example.testapp.di.MainDispatcher
import com.example.testapp.domain.models.CryptoData
import com.example.testapp.domain.models.CryptosPackage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Singleton
class CryptoRepository @Inject constructor(
    private val api: CoingeckoApiV3,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    val flowData: StateFlow<CryptosPackage>
        get() = _flowData
    private val _flowData: MutableStateFlow<CryptosPackage> = MutableStateFlow(CryptosPackage())

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

    init {
        tickerFlow(10.seconds).onEach {
            refresh()
        }.launchIn(CoroutineScope(ioDispatcher))
    }

    private suspend fun refresh() {
        if (!_flowData.value.loading) {
            withContext(mainDispatcher) {
                _flowData.value = _flowData.value.copy(
                    loading = true
                )
            }
            var value: List<CryptoData>
            var error = false
            try {
                value = api.getCrypto().map {
                    CryptoData(
                        it.id, it.symbol, it.imageUrl, it.price, it.name, it.change24H
                    )
                }

            } catch (e: Exception) {
                value = listOf()
                error = true
            }

            withContext(mainDispatcher) {
                _flowData.value = CryptosPackage(
                    data = value, loading = false, error = error
                )
            }
        }
    }
}