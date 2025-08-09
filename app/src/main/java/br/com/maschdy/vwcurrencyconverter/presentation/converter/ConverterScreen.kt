package br.com.maschdy.vwcurrencyconverter.presentation.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.maschdy.vwcurrencyconverter.domain.model.currencyCodes
import br.com.maschdy.vwcurrencyconverter.presentation.theme.VWCurrencyConverterTheme
import br.com.maschdy.vwcurrencyconverter.presentation.utils.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    navController: NavController = rememberNavController(),
    viewModel: ConverterViewModel = koinViewModel(),
    fromOptions: List<String> = currencyCodes,
    toOptions: List<String> = currencyCodes,
) {
    var value by remember { mutableStateOf("") }
    var actualCurrency by remember { mutableStateOf("") }
    var targetCurrency by remember { mutableStateOf("") }
    var expandedActualCurrency by remember { mutableStateOf(false) }
    var expandedTargetCurrency by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    val enabled =
        value.isNotBlank() && actualCurrency.isNotBlank() && targetCurrency.isNotBlank() && !uiState.isLoading

    fun onConvertClick() {
        viewModel.onEvent(
            ConverterUIEvent.ConvertCurrency(
                value.toBigDecimal(),
                actualCurrency,
                targetCurrency
            )
        )
    }

    fun onHistoryClick() {
        navController.navigate(Screen.HistoryScreen.route)
    }

    fun getValidValue(input: String, previousInput: String): String {
        if (input.isEmpty()) return ""

        val regex = Regex("^\\d+(\\.\\d{0,2})?$")
        return if (regex.matches(input)) input else previousInput
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Conversor de Moedas",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            actions = {
                TextButton(onClick = { onHistoryClick() }) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "Histórico",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Histórico")
                }
            }
        )

        OutlinedTextField(
            value = value,
            onValueChange = { input ->
                if (input.length <= 15) {
                    value = getValidValue(input, value)
                }
            },
            label = { Text("Valor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedActualCurrency,
                onExpandedChange = { expandedActualCurrency = !expandedActualCurrency },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = actualCurrency,
                    onValueChange = {},
                    label = { Text("Converter de:") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActualCurrency) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable)
                )
                ExposedDropdownMenu(
                    expanded = expandedActualCurrency,
                    onDismissRequest = { expandedActualCurrency = false }
                ) {
                    fromOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                actualCurrency = option
                                expandedActualCurrency = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedTargetCurrency,
                onExpandedChange = { expandedTargetCurrency = !expandedTargetCurrency },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = targetCurrency,
                    onValueChange = {},
                    label = { Text("Para:") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTargetCurrency) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable)
                )
                ExposedDropdownMenu(
                    expanded = expandedTargetCurrency,
                    onDismissRequest = { expandedTargetCurrency = false }
                ) {
                    toOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                targetCurrency = option
                                expandedTargetCurrency = false
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                onConvertClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = enabled
        ) {
            Text("Converter")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            if (uiState.errorMessage.isNotEmpty()) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (uiState.result.isNotEmpty()) {
                Text(
                    text = uiState.result,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
    VWCurrencyConverterTheme {
        ConverterScreen()
    }
}
