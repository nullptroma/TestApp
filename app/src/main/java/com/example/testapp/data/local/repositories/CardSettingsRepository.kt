package com.example.testapp.data.local.repositories

interface CardSettingsRepository<T> {
    suspend fun getById(id: Long):T
    suspend fun updateSetting(id: Long, setting: T)
}