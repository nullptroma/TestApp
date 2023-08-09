package com.example.testapp.data.local.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.model.DbEnabledCard
import com.example.testapp.domain.Cards
import com.example.testapp.domain.EnabledCard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnabledCardsRepository @Inject constructor(private val dao: EnabledCardsDao) {
    fun getAll(): LiveData<List<EnabledCard>> {
        return dao.getAll().map { list->list.map { EnabledCard(it.cardId, it.cardType, it.priority) } }
    }

    fun createCard(type: Cards, priority:Long=100){
        dao.add(DbEnabledCard(0, type, priority))
    }

    fun setAll(list: List<EnabledCard>){
        dao.deleteAll()
        dao.addAll(list.map { DbEnabledCard(it.id, it.type, it.priority) })
    }

    fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}