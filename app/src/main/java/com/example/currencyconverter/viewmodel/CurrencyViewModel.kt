package com.example.currencyconverter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.util.Response
import com.example.currencyconverter.util.SUPPORTED_COUNTRIES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_FROM_CURRENCY = "PLN"
const val DEFAULT_TO_CURRENCY = "UAH"
const val POLAND_INDEX = 0
const val GERMANY_INDEX = 1
const val UK_INDEX = 2
const val UKRAINE_INDEX = 3

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
            val result = repository.getCurrencyRate(from = from, to = to, amount = amount)
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

    fun updateFromCountry(country: Country, isFromCountry: Boolean) {
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
}

data class CurrencyUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var currencyConversion: CurrencyConversion? = null,
    var fromCountry: Country = SUPPORTED_COUNTRIES[POLAND_INDEX],
    var toCountry: Country = SUPPORTED_COUNTRIES[UKRAINE_INDEX],
    val countriesToDisplay: List<Country> = SUPPORTED_COUNTRIES
)