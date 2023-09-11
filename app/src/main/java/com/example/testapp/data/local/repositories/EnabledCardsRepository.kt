package com.example.testapp.data.local.repositories

import com.example.testapp.data.local.dao.EnabledCardsDao
import com.example.testapp.data.local.model.DbEnabledCard
import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.models.cardsettings.EnabledCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnabledCardsRepository @Inject constructor(
    private val _dao: EnabledCardsDao,
    private val _settingsCreator: SettingsCreator,
    @IoDispatcher private val _ioDispatcher: CoroutineDispatcher
) {
    init {
        flow<Unit> {
            getAll().collect {
                _settingsCreator.createSettings(it)
            }
        }.launchIn(CoroutineScope(_ioDispatcher))
    }

    fun getAll(): Flow<List<EnabledCard>> {
        return _dao.getAllFlow().map { list ->
            list.map { EnabledCard(it.cardId, it.cardType, it.priority) }
        }
    }

    suspend fun setAll(list: List<EnabledCard>) {
        withContext(_ioDispatcher) {
            val current = _dao.getAll()
            val remove = current.filter { setting -> list.all { it.id != setting.cardId } }
            val addOrig = list.filter { current.all { setting -> it.id != setting.cardId } }
            val add = addOrig.map { DbEnabledCard(it.id, it.type, it.priority) }
            val update = list.filter { current.any { setting -> it.id == setting.cardId } }
                .map { DbEnabledCard(it.id, it.type, it.priority) }
            _dao.deleteAll(remove)
            _dao.addAll(add)
            _dao.updateAll(update)
        }
    }
}