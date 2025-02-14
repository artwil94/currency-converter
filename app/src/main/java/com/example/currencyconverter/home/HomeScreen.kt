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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.composable.ChangeSystemBarColor
import com.example.currencyconverter.composable.CurrencyConverter
import com.example.currencyconverter.composable.NoNetworkAlert
import com.example.currencyconverter.composable.SearchBottomSheet
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.network.ConnectivityObserver
import com.example.currencyconverter.network.NetworkConnectivityObserver
import com.example.currencyconverter.ui.theme.TgTheme
import com.example.currencyconverter.viewmodel.CurrencyUIState
import com.example.currencyconverter.viewmodel.CurrencyViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: CurrencyViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val connectivityObserver = remember { NetworkConnectivityObserver(context) }
    val networkStatus by connectivityObserver.observe().collectAsState(
        initial = connectivityObserver.getCurrentStatus()
    )
    val isNoNetwork = networkStatus == ConnectivityObserver.Status.Lost ||
            networkStatus == ConnectivityObserver.Status.Unavailable
    val fromCurrency by remember { mutableStateOf(uiState.fromCountry.currency) }
    val toCurrency by remember { mutableStateOf(uiState.toCountry.currency) }
    var sendingAmount by remember { mutableFloatStateOf(uiState.sendingAmount) }
    var updateFromCountry by remember { mutableStateOf(true) }
    var showNoNetworkAlert by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        if (sendingAmount > 0 && sendingAmount < uiState.fromCountry.sendingLimit) {
            viewModel.getCurrencyRates(from = fromCurrency, to = toCurrency, amount = sendingAmount)
        }
        if (isNoNetwork) {
            showNoNetworkAlert = true
        }
    }
    LaunchedEffect(key1 = isNoNetwork) {
        if (isNoNetwork) {
            showNoNetworkAlert = true
        } else {
            showNoNetworkAlert = false
            viewModel.getCurrencyRates(
                from = fromCurrency,
                to = toCurrency,
                amount = sendingAmount
            )
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
                supportedCountries = uiState.countriesToDisplay,
                onSendingAmountChange = { value ->
                        sendingAmount = value.toFloatOrNull() ?: 0f
                        if (sendingAmount > 0 && sendingAmount < uiState.fromCountry.sendingLimit &&
                            networkStatus == ConnectivityObserver.Status.Available
                        ) {
                            viewModel.getCurrencyRates(
                                from = fromCurrency,
                                to = toCurrency,
                                amount = sendingAmount
                            )
                        }
                        if (isNoNetwork) {
                            showNoNetworkAlert = true
                        }
                },
                onFromCountryUpdate = {
                    updateFromCountry = true
                    keyboardController?.hide()
                },
                onToCountryUpdate = {
                    updateFromCountry = false
                    keyboardController?.hide()
                },
                onCountry = { country, isFromCountry ->
                    viewModel.updateCountry(country = country, isFromCountry = isFromCountry)
                },
                onSearchInputChange = {
                    viewModel.searchCountry(it)
                },
                isFromCountry = updateFromCountry,
                error = sendingAmount > uiState.fromCountry.sendingLimit || sendingAmount == 0f,
                errorMessage = when {
                    sendingAmount > uiState.fromCountry.sendingLimit -> stringResource(
                        id = R.string.maximum_sending_amount
                    ) + ": " + "${uiState.fromCountry.sendingLimit} ${uiState.fromCountry.currency} "

                    sendingAmount == 0f -> stringResource(id = R.string.amount_too_low)
                    else -> ""

                },
                onSwitchIcon = { viewModel.switchCurrencies() }
            )
        }
        if (showNoNetworkAlert) {
            NoNetworkAlert(onClose = { showNoNetworkAlert = false })
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContent(
    uiState: CurrencyUIState,
    supportedCountries: List<Country>,
    onSendingAmountChange: (String) -> Unit,
    onFromCountryUpdate: () -> Unit,
    onToCountryUpdate: () -> Unit,
    onDone: () -> Unit = {},
    onSearchInputChange: (String) -> Unit,
    onCountry: (Country, Boolean) -> Unit,
    isFromCountry: Boolean = false,
    error: Boolean = false,
    errorMessage: String,
    onSwitchIcon: () -> Unit
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
                error = error,
                errorMessage = errorMessage,
                onSwitchIcon = {
                    onSwitchIcon.invoke()
                }
            )
        }
    }
    SearchBottomSheet(
        title = stringResource(
            id = if (isFromCountry) R.string.sending_from
            else R.string.sending_to
        ),
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