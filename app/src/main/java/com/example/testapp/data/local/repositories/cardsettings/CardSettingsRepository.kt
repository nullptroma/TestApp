package com.example.testapp.data.local.repositories.cardsettings

import kotlinx.coroutines.flow.Flow

interface CardSettingsRepository<T> {
    suspend fun checkIds(ids : List<Long>)
    suspend fun getForIds(): Flow<Map<Long, T>>
    suspend fun setSettings(settings: Map<Long, T>)
}