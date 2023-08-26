package com.example.testapp.data.remote.repositories

import com.example.testapp.data.remote.api.OpenWeatherApi
import com.example.testapp.data.remote.model.weather.RemoteWeather
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.Coordinates
import com.example.testapp.domain.models.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: OpenWeatherApi, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun get(pos: Coordinates): Weather? {
        var res: RemoteWeather
        try{
            withContext(ioDispatcher) {
                res = api.getWeather(pos.latitude, pos.longitude)
            }
        }catch (e:Exception) {
            return null
        }
        return Weather(
            res.name,
            res.weather[0].icon,
            res.wind.speed,
            res.wind.deg,
            res.main.pressure,
            res.main.humidity,
            res.clouds.all,
            res.weather[0].description,
            res.main.temp,
            res.main.feels_like
        )
    }
}