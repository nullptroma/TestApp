package com.example.testapp.data.remote.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.data.remote.api.CoingeckoApiV3
import com.example.testapp.di.IoDispatcher
import com.example.testapp.di.MainDispatcher
import com.example.testapp.domain.models.CryptoData
import com.example.testapp.domain.models.CryptosPackage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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
    val liveData: LiveData<CryptosPackage>
        get() = _liveData
    private val _liveData: MutableLiveData<CryptosPackage> = MutableLiveData<CryptosPackage>()

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

    init {
        tickerFlow(10.seconds).onEach {
            refreshIfHasActiveObservers()
        }.launchIn(CoroutineScope(ioDispatcher))
    }

    private suspend fun refreshIfHasActiveObservers() {
        if (_liveData.hasActiveObservers()) refresh()
    }

    private suspend fun refresh() {
        if (_liveData.value == null || !_liveData.value!!.loading) {
            withContext(mainDispatcher) {
                _liveData.value =
                    if (_liveData.value == null) CryptosPackage(loading = true)
                    else _liveData.value!!.copy(
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
                _liveData.value = CryptosPackage(
                    data = value, loading = false, error = error
                )
            }
        }
    }
}