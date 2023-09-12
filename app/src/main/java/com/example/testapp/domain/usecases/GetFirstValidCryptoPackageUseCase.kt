package com.example.testapp.domain.usecases

import com.example.testapp.data.remote.repositories.CryptoRepository
import com.example.testapp.domain.models.crypto.CryptosPackage
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFirstValidCryptoPackageUseCase @Inject constructor(private val repository: CryptoRepository){
    private lateinit var _package: CryptosPackage
    private var _initialized = false


    init {
    }

    suspend fun get() : CryptosPackage {

        if(_initialized)
            return _package

        while(!::_package.isInitialized)
            delay(100)

        _initialized = true
        return _package //??
    }
}