package br.com.maschdy.vwcurrencyconverter.domain.datastore

import br.com.maschdy.vwcurrencyconverter.domain.model.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryDataStore {
    val historyFlow: Flow<Array<HistoryItem>>
    suspend fun saveItem(item: HistoryItem)
}
