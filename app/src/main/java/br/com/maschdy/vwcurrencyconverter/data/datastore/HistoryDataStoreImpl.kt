package br.com.maschdy.vwcurrencyconverter.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.maschdy.vwcurrencyconverter.domain.datastore.HistoryDataStore
import br.com.maschdy.vwcurrencyconverter.domain.model.HistoryItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryDataStoreImpl(private val context: Context) : HistoryDataStore {
    private val Context.dataStore by preferencesDataStore(name = "history_store")
    private val HISTORY_KEY = stringPreferencesKey("history_list")

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val type = Types.newParameterizedType(List::class.java, HistoryItem::class.java)
    private val adapter = moshi.adapter<List<HistoryItem>>(type)

    override val historyFlow: Flow<Array<HistoryItem>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[HISTORY_KEY] ?: "[]"
            adapter.fromJson(json)?.toTypedArray() ?: emptyArray()
        }

    override suspend fun saveItem(item: HistoryItem) {
        context.dataStore.edit { preferences ->
            val currentListJson = preferences[HISTORY_KEY] ?: "[]"
            val currentList = adapter.fromJson(currentListJson)?.toMutableList() ?: mutableListOf()
            currentList.add(item)
            val updatedJson = adapter.toJson(currentList)
            preferences[HISTORY_KEY] = updatedJson
        }
    }
}
