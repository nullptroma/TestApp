package com.example.testapp.data.local.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.model.DbEnabledCard
import com.example.testapp.domain.CardType
import com.example.testapp.domain.models.EnabledCard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnabledCardsRepository @Inject constructor(private val dao: EnabledCardsDao) {
    fun getAll(): LiveData<List<EnabledCard>> {
        return dao.getAllLive().map { list->list.map { EnabledCard(it.cardId, it.cardType, it.priority) } }
    }

    fun createCard(type: CardType, priority:Long=100){
        dao.add(DbEnabledCard(0, type, priority))
    }

    fun setAll(list: List<EnabledCard>){
        val current = dao.getAll()
        val remove = current.filter { setting -> list.all { it.id != setting.cardId  } }
        val add = list.filter { current.all { setting -> it.id != setting.cardId  } }.map { DbEnabledCard(it.id, it.type, it.priority) }
        val update = list.filter { current.any { setting -> it.id == setting.cardId  } }.map { DbEnabledCard(it.id, it.type, it.priority) }
        dao.deleteAll(remove)
        dao.addAll(add)
        dao.updateAll(update)
    }

    fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}