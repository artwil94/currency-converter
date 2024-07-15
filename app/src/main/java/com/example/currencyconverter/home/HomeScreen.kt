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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.currencyconverter.R
import com.example.currencyconverter.composable.CountryItem
import com.example.currencyconverter.composable.CurrencyConverter
import com.example.currencyconverter.composable.SearchBottomSheet
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.ui.theme.TgTheme
import com.example.currencyconverter.viewmodel.RatesUIState
import com.example.currencyconverter.viewmodel.RatesViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContent(
    uiState: RatesUIState,
) {
    val supportedCountries = listOf(
        Country(
            name = "Poland",
            currency = "PLN",
            currencyName = "Polish zloty",
            sendingLimit = 20000,
            icon = R.drawable.ic_poland_big
        ),
        Country(
            name = "Germany",
            currency = "EUR",
            currencyName = "Euro",
            sendingLimit = 5000,
            icon = R.drawable.ic_germany_big
        ),
        Country(
            name = "Great Britain",
            currency = "GBP",
            currencyName = "British Pound",
            sendingLimit = 1000,
            icon = R.drawable.ic_uk_big
        ),
        Country(
            name = "Ukraine",
            currency = "UAH",
            currencyName = "Hrivna",
            sendingLimit = 50000,
            icon = R.drawable.ic_ukraine_big
        )
    )
    val searchBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TgTheme.tGDimensions.padding)
    ) {
        Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingSeparator))
        CurrencyConverter(onChevronDownClick = {
            coroutineScope.launch {
                searchBottomSheetState.show()
            }
        })
    }
    SearchBottomSheet(
        title = stringResource(id = R.string.sending_to),
        bottomSheetState = searchBottomSheetState, countries = supportedCountries
    )
}