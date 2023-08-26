package com.example.testapp.domain.usecases.get_settings

import com.example.testapp.data.local.repositories.CardSettingsRepository

class UseCardSettingsUseCase<T>(private val _repo : CardSettingsRepository<T>){
    suspend fun read(id:Long): T {
        return _repo.getById(id)
    }

    suspend fun updateSetting(id: Long, setting: T) {
        _repo.updateSetting(id, setting)
    }
}