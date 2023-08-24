package com.example.testapp.di

import com.example.testapp.data.remote.api.CoingeckoApiV3
import com.example.testapp.data.remote.api.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoingeckoRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeatherRetrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    @CoingeckoRetrofit
    fun provideCoingeckoRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.coingecko.com/api/v3/")
            .build()
    }

    @Singleton
    @Provides
    @OpenWeatherRetrofit
    fun provideOpenWeatherRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/")
            .build()
    }

    @Provides
    fun provideCoingeckoApi(@CoingeckoRetrofit retrofit: Retrofit): CoingeckoApiV3 {
        return retrofit.create(CoingeckoApiV3::class.java)
    }

    @Provides
    fun provideOpenWeatherApi(@OpenWeatherRetrofit retrofit: Retrofit): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }
}