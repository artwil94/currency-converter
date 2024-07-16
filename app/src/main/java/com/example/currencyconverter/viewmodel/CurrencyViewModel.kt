package com.example.currencyconverter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.util.POLAND_INDEX
import com.example.currencyconverter.util.Response
import com.example.currencyconverter.util.SUPPORTED_COUNTRIES
import com.example.currencyconverter.util.UKRAINE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_FROM_CURRENCY = "PLN"
const val DEFAULT_TO_CURRENCY = "UAH"

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    var uiState by mutableStateOf(CurrencyUIState())
        private set

    fun getCurrencyRates(from: String, to: String, amount: Float) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true
            )
            when (val result = repository.getCurrencyRate(from = from, to = to, amount = amount)) {
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

    fun updateCountry(country: Country, isFromCountry: Boolean) {
        viewModelScope.launch {
            uiState = if (isFromCountry) {
                uiState.copy(
                    isLoading = false,
                    fromCountry = country,
                    countriesToDisplay = SUPPORTED_COUNTRIES
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    toCountry = country,
                    countriesToDisplay = SUPPORTED_COUNTRIES
                )
            }
        }
        getCurrencyRates(
            from = uiState.fromCountry.currency,
            to = uiState.toCountry.currency,
            amount = uiState.currencyConversion?.fromAmount ?: 0.0f
        )

    }

    fun searchCountry(input: String?) {
        viewModelScope.launch {
            uiState = if (input.isNullOrBlank()) {
                uiState.copy(
                    countriesToDisplay = SUPPORTED_COUNTRIES
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    countriesToDisplay = SUPPORTED_COUNTRIES.filter {
                        it.name.contains(input, ignoreCase = true)
                    }
                )
            }
        }
    }

    fun switchCurrencies() {
        val fromCountry = uiState.fromCountry
        uiState = uiState.copy(
            isLoading = true,
            fromCountry = uiState.toCountry,
            toCountry = fromCountry
        )
        getCurrencyRates(
            from = uiState.fromCountry.currency,
            to = uiState.toCountry.currency,
            amount = uiState.currencyConversion?.fromAmount ?: 0.0f
        )
    }
}

data class CurrencyUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var currencyConversion: CurrencyConversion? = null,
    var fromCountry: Country = SUPPORTED_COUNTRIES[POLAND_INDEX],
    var toCountry: Country = SUPPORTED_COUNTRIES[UKRAINE_INDEX],
    var sendingAmount: Float = 300f,
    val countriesToDisplay: List<Country> = SUPPORTED_COUNTRIES
)