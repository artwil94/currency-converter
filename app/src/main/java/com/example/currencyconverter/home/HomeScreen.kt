package com.example.currencyconverter.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.currencyconverter.composable.CurrencyConverter
import com.example.currencyconverter.ui.theme.TgTheme
import com.example.currencyconverter.viewmodel.RatesUIState
import com.example.currencyconverter.viewmodel.RatesViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavHostController, viewModel: RatesViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    var fromCurrency by remember { mutableStateOf("PLN") }
    var toCurrency by remember { mutableStateOf("UAH") }
    var amount by remember { mutableStateOf(300f) }
    LaunchedEffect(key1 = Unit) {
        viewModel.getCurrencyRates(from = fromCurrency, to = toCurrency, amount = amount)

    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            HomeScreenContent(
                uiState = uiState
            )

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreenContent(
    uiState: RatesUIState,
) {
    val supportedCurrencies = listOf(
        Triple("Poland", "PLN", 20000),
        Triple("Germany", "EUR", 5000),
        Triple("Great Britain", "GBP", 1000),
        Triple("Ukraine", "UAH", 50000)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TgTheme.tGDimensions.padding)
    ) {
        Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingSeparator))
        CurrencyConverter()

        if (uiState.currencyConversion != null) {
//            Text(text = uiState.rate?.from.toString())
//            Text(text = uiState.rate?.to.toString())
//            Text(text = uiState.rate?.rate.toString())
//            Text(text = uiState.rate?.toAmount.toString())
//            Text(text = uiState.rate?.fromAmount.toString())
        }
    }
}