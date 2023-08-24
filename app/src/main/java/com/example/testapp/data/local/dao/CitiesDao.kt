package com.example.testapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.data.local.model.DbCity

@Dao
interface CitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(city: DbCity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(city: List<DbCity>)

    @Query("SELECT * FROM cities")
    fun getAll(): LiveData<List<DbCity>>
}