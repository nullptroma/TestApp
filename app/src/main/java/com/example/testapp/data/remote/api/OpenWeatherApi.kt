package com.example.testapp.data.remote.api

import com.example.testapp.data.remote.model.weather.RemoteWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = "db64831e91fc555ac698f36c7292277a",
    ): RemoteWeather
}