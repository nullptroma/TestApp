package com.example.testapp.domain.usecases

import com.example.testapp.data.remote.repositories.CryptoRepository
import com.example.testapp.domain.models.crypto.CryptosPackage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFirstValidCryptoPackageUseCase @Inject constructor(private val _repository: CryptoRepository) {
    private var _package: CryptosPackage? = null
    private var job: Job? = null

    init {
        job = CoroutineScope(Dispatchers.Default).launch {
            _repository.flowData.collect {
                if (it.data.isNotEmpty()) {
                    _package = it
                    while(job == null)
                        delay(10)
                    job?.cancel()
                }
            }
        }
    }

    suspend fun get(): CryptosPackage {
        while (_package == null)
            delay(100)

        return _package!!
    }
}