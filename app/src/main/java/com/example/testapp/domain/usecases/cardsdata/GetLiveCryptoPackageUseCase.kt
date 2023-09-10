package com.example.testapp.domain.usecases.cardsdata

import com.example.testapp.data.remote.repositories.CryptoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLiveCryptoPackageUseCase @Inject constructor(
    dataRepo: CryptoRepository,
) {
    val liveData = dataRepo.flowData
}