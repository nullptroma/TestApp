package com.codingtroops.restaurantsapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.data.local.TestAppDb
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    fun provideWeatherDao(database: TestAppDb): WeatherDao {
        return database.weatherDao
    }

    @Provides
    fun provideEnabledCardsDao(database: TestAppDb): EnabledCardsDao {
        return database.enabledCardsDao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext appContext: Context
    ): TestAppDb {
        return Room.databaseBuilder(
            appContext, TestAppDb::class.java, "settings_database"
        ).fallbackToDestructiveMigration().build()
    }

//    @Singleton
//    @Provides
//    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://testrestaurants-bc847-default-rtdb.europe-west1.firebasedatabase.app/")
//            .build()
//    }

//    @Provides
//    fun provideRetrofitApi(retrofit: Retrofit): RestaurantsApiService {
//        return retrofit.create(RestaurantsApiService::class.java)
//    }
}