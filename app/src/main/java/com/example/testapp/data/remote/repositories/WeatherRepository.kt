package com.example.testapp.data.remote.repositories

import android.util.Log
import com.example.testapp.data.remote.api.OpenWeatherApi
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.Coordinates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: OpenWeatherApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun get(pos: Coordinates) {
        withContext(ioDispatcher) {
            val res = api.getWeather(pos.latitude, pos.longitude)
            Log.d("MyTag", "ПОлучение погоды! ${res.name}")
        }
    }
}