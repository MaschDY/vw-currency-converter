package br.com.maschdy.vwcurrencyconverter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.maschdy.vwcurrencyconverter.presentation.converter.ConverterScreen
import br.com.maschdy.vwcurrencyconverter.presentation.history.HistoryScreen
import br.com.maschdy.vwcurrencyconverter.presentation.theme.VWCurrencyConverterTheme
import br.com.maschdy.vwcurrencyconverter.presentation.utils.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VWCurrencyConverterTheme {
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
