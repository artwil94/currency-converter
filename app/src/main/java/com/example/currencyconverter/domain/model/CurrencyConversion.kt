package com.example.currencyconverter.domain.model

import com.example.currencyconverter.data.remote.CurrencyConversionDto

data class CurrencyConversion(
    val from: String,
    val to: String,
    val rate: Double,
    val fromAmount: Float,
    val toAmount: Float
)

fun CurrencyConversionDto.toCurrencyRate(): CurrencyConversion? {
    return if (from != null && to != null && rate != null && fromAmount != null && toAmount != null) {
        CurrencyConversion(
            from = from,
            to = to,
            rate = rate,
            fromAmount = fromAmount,
            toAmount = toAmount
        )
    } else {
        null
    }
}