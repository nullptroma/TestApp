package com.example.testapp.domain.usecases

import com.example.testapp.data.local.repositories.EnabledCardsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFlowEnabledCardsUseCase @Inject constructor(
    repo: EnabledCardsRepository,
) {
    val flowData = repo.getAll()
}