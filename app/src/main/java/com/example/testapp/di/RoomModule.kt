package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testapp.R
import com.example.testapp.data.local.TestAppDb
import com.example.testapp.data.local.dao.CitiesDao
import com.example.testapp.data.local.dao.CryptoDao
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.dao.MapDao
import com.example.testapp.data.local.dao.WeatherDao
import com.example.testapp.data.local.model.DbCity
import com.example.testapp.data.local.model.jsonCity.JsonCity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer
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
                        val list = readCities(appContext).map { city -> DbCity(0, city.name, "RU", city.coords.lat, city.coords.lat) }
                        dao.add(list)
                    }
                }
            }
        }
        room = Room.databaseBuilder(
            appContext, TestAppDb::class.java, "settings_database"
        ).fallbackToDestructiveMigration().addCallback(callback).build()
        return room;
    }

    private fun readCities(context: Context): List<JsonCity> {
        val input: InputStream = context.resources.openRawResource(R.raw.russian_cities)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(input, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            input.close()
        }

        val jsonString: String = writer.toString()
        val itemType = object : TypeToken<List<JsonCity>>() {}.type
        return Gson().fromJson(jsonString, itemType)
    }
}