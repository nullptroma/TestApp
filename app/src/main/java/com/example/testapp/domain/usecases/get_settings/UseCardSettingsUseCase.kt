package com.example.testapp.domain.usecases.get_settings

import com.example.testapp.data.local.repositories.cardsettings.CardSettingsRepository
import kotlinx.coroutines.flow.Flow

class UseCardSettingsUseCase<T>(private val _repo : CardSettingsRepository<T>){
    suspend fun getFlow(): Flow<Map<Long, T>> {
        return _repo.getForIds()
    }

    suspend fun updateSetting(settings: Map<Long, T>) {
        _repo.setSettings(settings)
    }
}