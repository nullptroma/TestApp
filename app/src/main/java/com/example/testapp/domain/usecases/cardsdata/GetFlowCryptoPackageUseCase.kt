package com.example.testapp.domain.usecases.cardsdata

import com.example.testapp.data.remote.repositories.CryptoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFlowCryptoPackageUseCase @Inject constructor(
    dataRepo: CryptoRepository,
) {
    val flowData = dataRepo.flowData
}