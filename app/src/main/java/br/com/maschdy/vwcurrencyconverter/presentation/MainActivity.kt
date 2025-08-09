package br.com.maschdy.vwcurrencyconverter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.maschdy.vwcurrencyconverter.presentation.converter.ConverterScreen
import br.com.maschdy.vwcurrencyconverter.presentation.history.HistoryScreen
import br.com.maschdy.vwcurrencyconverter.presentation.theme.VWCurrencyConverterTheme
import br.com.maschdy.vwcurrencyconverter.presentation.utils.Screen
import br.com.maschdy.vwcurrencyconverter.presentation.viewModel.PrefDataStoreViewModel
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val prefDataStoreViewModel: PrefDataStoreViewModel = koinViewModel()
            val preferences by prefDataStoreViewModel.preferences.collectAsState()
            val isDarkMode = preferences.isDarkMode

            VWCurrencyConverterTheme(isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ConverterScreen.route
                    ) {
                        composable(Screen.ConverterScreen.route) {
                            ConverterScreen(navController)
                        }
                        composable(Screen.HistoryScreen.route) {
                            HistoryScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
