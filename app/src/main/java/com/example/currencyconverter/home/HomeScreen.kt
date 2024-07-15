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
import androidx.navigation.NavHostController
import com.example.currencyconverter.R
import com.example.currencyconverter.composable.ChangeSystemBarColor
import com.example.currencyconverter.composable.CurrencyConverter
import com.example.currencyconverter.composable.SearchBottomSheet
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.ui.theme.TgTheme
import com.example.currencyconverter.viewmodel.RatesUIState
import com.example.currencyconverter.viewmodel.RatesViewModel
import kotlinx.coroutines.launch

const val POLAND_INDEX = 0
const val GERMANY_INDEX = 1
const val UK_INDEX = 2
const val UKRAINE_INDEX = 3

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavHostController, viewModel: RatesViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val supportedCountries = viewModel.supportedCountries
    var fromCurrency by remember { mutableStateOf(viewModel.from.value) }
    var toCurrency by remember { mutableStateOf(viewModel.to.value) }
    var sendingAmount = remember { mutableStateOf("300") }
//    val fromAmountFormatted = String.format("%.2f",sendingAmount.value.toDouble())
    LaunchedEffect(key1 = Unit) {
        if (sendingAmount.value.isNotBlank()) {
            val amount = sendingAmount.value.toFloat()
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
                supportedCountries = supportedCountries,
                onSendingAmountChange = { value ->
                    sendingAmount.value = value
                },
                onDone = {
                    if (sendingAmount.value.isNotBlank()) {
                        val amount = sendingAmount.value.toFloat()
                        viewModel.getCurrencyRates(
                            from = fromCurrency,
                            to = toCurrency,
                            amount = amount
                        )
                    }
                }
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContent(
    uiState: RatesUIState,
    fromAmount: String,
    supportedCountries: List<Country>,
    onSendingAmountChange: (String) -> Unit,
    onDone: () -> Unit,
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
                onChevronDownClick = {
                    coroutineScope.launch {
                        searchBottomSheetState.show()
                    }
                },
                onSendingAmountChange = onSendingAmountChange,
                onDone = onDone
            )
        }
    }
    SearchBottomSheet(
        title = stringResource(id = R.string.sending_to),
        bottomSheetState = searchBottomSheetState, countries = supportedCountries
    )
}