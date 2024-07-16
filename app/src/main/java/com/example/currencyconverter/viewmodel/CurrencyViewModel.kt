package com.example.currencyconverter.viewmodel

import androidx.compose.runtime.MutableState
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

    fun updateFromCountry(country: Country) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = false,
                fromCountry = country
            )
        }
        getCurrencyRates(
            from = uiState.fromCountry.currency,
            to = uiState.toCountry?.currency ?: DEFAULT_TO_CURRENCY,
            amount = uiState.currencyConversion?.fromAmount ?: 0.0f
        )
    }
    fun updateToCountry(country: Country) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = false,
                toCountry = country
            )
        }
        getCurrencyRates(
            from = uiState.fromCountry.currency,
            to = uiState.toCountry?.currency ?: DEFAULT_TO_CURRENCY,
            amount = uiState.currencyConversion?.fromAmount ?: 0.0f
        )
    }
}

data class RatesUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var currencyConversion: CurrencyConversion? = null,
    var fromCountry: Country = SUPPORTED_COUNTRIES.first(),
    var toCountry: Country? = null
)