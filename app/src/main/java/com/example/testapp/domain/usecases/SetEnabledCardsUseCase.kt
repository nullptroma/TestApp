package com.example.testapp.domain.usecases

import com.example.testapp.data.local.repositories.EnabledCardsRepository
import com.example.testapp.domain.models.cardsettings.EnabledCard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetEnabledCardsUseCase @Inject constructor(
    private val repo: EnabledCardsRepository,
) {
    suspend fun setAll(list: List<EnabledCard>) {
        repo.setAll(list)
    }
}