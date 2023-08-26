package com.example.testapp.domain.usecases

import com.example.testapp.data.local.repositories.EnabledCardsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLiveEnabledCardsUseCase @Inject constructor(
    repo: EnabledCardsRepository,
) {
    val liveData = repo.getAll()
}