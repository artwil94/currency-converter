package com.example.currencyconverter.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.viewmodel.RatesUIState
import com.example.currencyconverter.viewmodel.RatesViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: RatesViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    var fromCurrency by remember {
        mutableStateOf("PLN")
    }
    var toCurrency by remember {
        mutableStateOf("UAH")
    }
    var amount by remember {
        mutableStateOf(300f)
    }
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
    Column(modifier = Modifier.fillMaxSize()) {
        if (uiState.rate != null) {
            Text(text = uiState.rate?.from.toString())
            Text(text = uiState.rate?.to.toString())
            Text(text = uiState.rate?.rate.toString())
            Text(text = uiState.rate?.toAmount.toString())
            Text(text = uiState.rate?.fromAmount.toString())
        }
    }
}