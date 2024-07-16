package com.example.currencyconverter.home

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.composable.ChangeSystemBarColor
import com.example.currencyconverter.composable.CurrencyConverter
import com.example.currencyconverter.composable.SearchBottomSheet
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.ui.theme.TgTheme
import com.example.currencyconverter.viewmodel.CurrencyUIState
import com.example.currencyconverter.viewmodel.CurrencyViewModel
import kotlinx.coroutines.launch

const val DEFAULT_SENDING_AMOUNT = "300"

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: CurrencyViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val fromCurrency by remember {
        mutableStateOf(uiState.fromCountry.currency)
    }
    val toCurrency by remember { mutableStateOf(uiState.toCountry.currency) }
    val sendingAmount = remember { mutableStateOf(DEFAULT_SENDING_AMOUNT) }
    val amount = sendingAmount.value.toFloatOrNull() ?: 0.0f
//    val fromAmountFormatted = String.format("%.2f", amount)
    var updateFromCountry by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = Unit) {
        if (amount > 0 && amount < uiState.fromCountry.sendingLimit) {
            viewModel.getCurrencyRates(from = fromCurrency, to = toCurrency, amount = amount)
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            HomeScreenContent(
                uiState = uiState,
                fromAmount = sendingAmount.value,
                supportedCountries = uiState.countriesToDisplay,
                onSendingAmountChange = { value ->
                    sendingAmount.value = value
                },
                onDone = {
                    if (amount > 0 && amount < uiState.fromCountry.sendingLimit) {
                        viewModel.getCurrencyRates(
                            from = fromCurrency,
                            to = toCurrency,
                            amount = amount
                        )
                    }
                },
                onFromCountryUpdate = {
                    updateFromCountry = true
                },
                onToCountryUpdate = {
                    updateFromCountry = false
                },
                onCountry = { country, isFromCountry ->
                    viewModel.updateFromCountry(country = country, isFromCountry)
                },
                onSearchInputChange = {
                    viewModel.searchCountry(it)
                },
                isFromCountry = updateFromCountry,
                error = amount > uiState.fromCountry.sendingLimit
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContent(
    uiState: CurrencyUIState,
    fromAmount: String,
    supportedCountries: List<Country>,
    onSendingAmountChange: (String) -> Unit,
    onFromCountryUpdate: () -> Unit,
    onToCountryUpdate: () -> Unit,
    onDone: () -> Unit,
    onSearchInputChange: (String) -> Unit,
    onCountry: (Country, Boolean) -> Unit,
    isFromCountry: Boolean = false,
    error: Boolean = false
) {
    val searchBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    ChangeSystemBarColor(statusBarColor = Color.White)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TgTheme.tGDimensions.padding)
    ) {
        Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingSeparator))
        uiState.currencyConversion?.let {
            CurrencyConverter(
                currencyConversion = it,
                fromAmount = fromAmount,
                onFromCountryUpdate = {
                    coroutineScope.launch {
                        searchBottomSheetState.show()
                    }
                    onFromCountryUpdate.invoke()
                },
                onToCountryUpdate = {
                    coroutineScope.launch {
                        searchBottomSheetState.show()
                    }
                    onToCountryUpdate.invoke()
                },
                onSendingAmountChange = onSendingAmountChange,
                onDone = onDone,
                fromCountry = uiState.fromCountry,
                toCountry = uiState.toCountry,
                error = error
            )
        }
    }
    SearchBottomSheet(
        title = stringResource(id = R.string.sending_to),
        bottomSheetState = searchBottomSheetState, countries = supportedCountries,
        onCountry = { country, isFromCountry ->
            onCountry.invoke(country, isFromCountry)
        },
        isFromCountry = isFromCountry,
        onSearchInputChange = {
            onSearchInputChange.invoke(it)
        }
    )
}