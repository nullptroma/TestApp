package com.example.testapp.data.local.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.testapp.data.local.dao.CitiesDao
import com.example.testapp.domain.models.CityInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepository @Inject constructor(private val dao: CitiesDao) {
    fun getAll(): LiveData<List<CityInfo>> {
        return dao.getAll().map { it.map { item -> item.toCityInfo() } }
    }
}