package com.example.currencyconverter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.CurrencyRate
import com.example.currencyconverter.domain.repository.CurrencyRatesRepository
import com.example.currencyconverter.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val repository: CurrencyRatesRepository
) : ViewModel() {

    var uiState by mutableStateOf(RatesUIState())
        private set

    fun getCurrencyRates(from: String, to: String, amount: Float) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true
            )
            val result = repository.getCurrencyRates(from = from, to = to, amount = amount)
            Timber.d("ARTUR ${result.message}")
            when (result) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        rate = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Response.Error -> {
                    uiState = uiState.copy(
                        rate = null,
                        error = "API response error",
                        isLoading = false
                    )
                }
            }
        }
    }

}


data class RatesUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var rate: CurrencyRate? = null
)