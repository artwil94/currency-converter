package com.example.currencyconverter.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val DEFAULT_FROM_CURRENCY = "PLN"
const val DEFAULT_TO_CURRENCY = "UAH"

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    var uiState by mutableStateOf(RatesUIState())
        private set
    val from: MutableState<String> = mutableStateOf(DEFAULT_FROM_CURRENCY)
    val to: MutableState<String> = mutableStateOf(DEFAULT_TO_CURRENCY)
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
//    val fromCountry: MutableState<Country> = mutableStateOf(supportedCountries[0])

    fun getCurrencyRates(from: String, to: String, amount: Float) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true
            )
            val result = repository.getCurrencyRate(from = from, to = to, amount = amount)
            Timber.d("ARTUR ${result.message}")
            when (result) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        currencyConversion = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Response.Error -> {
                    uiState = uiState.copy(
                        currencyConversion = null,
                        error = "API response error",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateCountry(country: Country) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = false,
                fromCountry = country
            )
            if (uiState.fromCountry?.currency != null && uiState.toCountry?.currency != null && uiState.currencyConversion != null)
                getCurrencyRates(
                    from = uiState.fromCountry?.currency ?: DEFAULT_FROM_CURRENCY,
                    to = uiState.toCountry?.currency ?: DEFAULT_TO_CURRENCY,
                    amount = uiState.currencyConversion?.fromAmount ?: 0.0f
                )
        }
    }
}

data class RatesUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var currencyConversion: CurrencyConversion? = null,
    var fromCountry: Country? = null,
    var toCountry: Country? = null
)