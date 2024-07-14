package com.example.currencyconverter.data.remote

import com.google.gson.annotations.SerializedName

data class CurrencyRateDto(
    @SerializedName("from") val from: String? = null,
    @SerializedName("to") val to: String? = null,
    @SerializedName("rate") val rate: Double? = null,
    @SerializedName("fromAmount") val fromAmount: Int? = null,
    @SerializedName("toAmount") val toAmount: Double? = null
)