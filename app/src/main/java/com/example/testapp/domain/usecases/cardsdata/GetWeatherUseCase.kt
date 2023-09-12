package com.example.testapp.domain.usecases.cardsdata

import com.example.testapp.data.remote.repositories.WeatherRepository
import com.example.testapp.domain.models.city.Coordinates
import com.example.testapp.domain.models.Weather
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetWeatherUseCase @Inject constructor(private val _dataRepo: WeatherRepository) {
    suspend fun get(coords: Coordinates): Weather? {
        return _dataRepo.get(coords)
    }
}