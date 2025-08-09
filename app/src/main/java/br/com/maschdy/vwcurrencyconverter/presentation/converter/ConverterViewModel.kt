package br.com.maschdy.vwcurrencyconverter.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ConverterViewModel : ViewModel() {

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
    ) =
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(3000)
            _uiState.value = _uiState.value.copy(result = "10")
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
