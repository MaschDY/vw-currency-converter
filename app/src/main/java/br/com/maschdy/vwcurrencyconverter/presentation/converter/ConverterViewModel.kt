package br.com.maschdy.vwcurrencyconverter.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.maschdy.vwcurrencyconverter.domain.datastore.HistoryDataStore
import br.com.maschdy.vwcurrencyconverter.domain.model.Error
import br.com.maschdy.vwcurrencyconverter.domain.model.Exception
import br.com.maschdy.vwcurrencyconverter.domain.model.HistoryItem
import br.com.maschdy.vwcurrencyconverter.domain.model.Success
import br.com.maschdy.vwcurrencyconverter.domain.repository.ConverterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ConverterViewModel(
    private val repository: ConverterRepository,
    private val historyDataStore: HistoryDataStore
) : ViewModel() {

    private val _uiState: MutableStateFlow<ConverterUIState> = MutableStateFlow(ConverterUIState())
    val uiState: StateFlow<ConverterUIState> get() = _uiState

    fun onEvent(event: ConverterUIEvent) {
        when (event) {
            is ConverterUIEvent.ConvertCurrency -> handleConvertCurrency(
                event.value,
                event.actualCurrency,
                event.targetCurrency
            )
        }
    }

    private fun handleConvertCurrency(
        value: BigDecimal,
        actualCurrency: String,
        targetCurrency: String
    ) = viewModelScope.launch {
        _uiState.value = ConverterUIState()
        _uiState.value = _uiState.value.copy(isLoading = true)
        _uiState.value = when (val response = repository.convertCurrency(
            value = value,
            from = actualCurrency,
            to = targetCurrency
        )) {
            is Success -> {
                val query = response.data.query
                val result = response.data.result
                val resultText = "${"%.2f".format(query.amount)} ${query.from} vale " +
                        "${"%.2f".format(result)} ${query.to}"
                val historyItem =
                    HistoryItem(name = "${query.from}/${query.to}", value = resultText)
                historyDataStore.saveItem(historyItem)

                _uiState.value.copy(result = resultText)
            }

            is Error -> _uiState.value.copy(errorMessage = response.message)
            is Exception -> _uiState.value.copy(errorMessage = response.message)
        }
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}

sealed class ConverterUIEvent {
    data class ConvertCurrency(
        val value: BigDecimal,
        val actualCurrency: String,
        val targetCurrency: String
    ) : ConverterUIEvent()
}

data class ConverterUIState(
    val result: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
