package com.codingtroops.restaurantsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testapp.data.local.CitiesLib
import com.example.testapp.data.local.TestAppDb
import com.example.testapp.data.local.dao.CitiesDao
import com.example.testapp.data.local.dao.CryptoDao
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.dao.MapDao
import com.example.testapp.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideCryptoDao(database: TestAppDb): CryptoDao {
        return database.cryptoDao
    }

    @Provides
    fun provideWeatherDao(database: TestAppDb): WeatherDao {
        return database.weatherDao
    }

    @Provides
    fun provideMapDao(database: TestAppDb): MapDao {
        return database.mapDao
    }

    @Provides
    fun provideEnabledCardsDao(database: TestAppDb): EnabledCardsDao {
        return database.enabledCardsDao
    }

    @Provides
    fun provideCitiesDao(database: TestAppDb): CitiesDao {
        return database.citiesDao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext appContext: Context
    ): TestAppDb {
        var room: TestAppDb? = null
        val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                Executors.newSingleThreadExecutor().execute {
                    room?.let {
                        val dao = it.citiesDao
                        dao.add(CitiesLib.list)
                    }
                }
            }
        }
        room = Room.databaseBuilder(
            appContext, TestAppDb::class.java, "settings_database"
        ).fallbackToDestructiveMigration().addCallback(callback).build()
        return room;
    }
}