package com.example.testapp.domain.usecases

import com.example.testapp.data.local.repositories.CitiesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFlowCitiesUseCate @Inject constructor(
    repo: CitiesRepository
) {
    val flowData = repo.getAll()
}