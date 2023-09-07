package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.model.DbEnabledCard
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.cardsettings.EnabledCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnabledCardsRepository @Inject constructor(
    private val dao: EnabledCardsDao, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getAll(): Flow<List<EnabledCard>> {
        return dao.getAllFlow().map { list ->
            list.map { EnabledCard(it.cardId, it.cardType, it.priority) }
        }
    }

    suspend fun setAll(list: List<EnabledCard>) {
        withContext(ioDispatcher) {
            val current = dao.getAll()
            val remove = current.filter { setting -> list.all { it.id != setting.cardId } }
            val add = list.filter { current.all { setting -> it.id != setting.cardId } }
                .map { DbEnabledCard(it.id, it.type, it.priority) }
            val update = list.filter { current.any { setting -> it.id == setting.cardId } }
                .map { DbEnabledCard(it.id, it.type, it.priority) }
            dao.deleteAll(remove)
            dao.addAll(add)
            dao.updateAll(update)
        }
    }
}