package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.remote.FxRatesApi
import com.example.currencyconverter.domain.model.CurrencyRate
import com.example.currencyconverter.domain.model.toCurrencyRate
import com.example.currencyconverter.util.Response
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val fxRatesApi: FxRatesApi
) : CurrencyRatesRepository {
    override suspend fun getCurrencyRates(
        from: String,
        to: String,
        amount: Float
    ): Response<CurrencyRate> {
        return try {
            val result =
                fxRatesApi.getFxRates(from = from, to = to, amount = amount).toCurrencyRate()
            Response.Success(
                data = result
            )
        } catch (e: Exception) {
            Response.Error(message = e.message)
        }
    }
}