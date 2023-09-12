package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.CitiesDao
import com.example.testapp.domain.models.city.CityInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepository @Inject constructor(private val dao: CitiesDao) {
    fun getAll(): Flow<List<CityInfo>> {
        return dao.getAll().map { it.map { item -> item.toCityInfo() } }
    }
}