package com.example.currencyconverter.util

import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.Country

const val POLAND_INDEX = 0
const val GERMANY_INDEX = 1
const val UK_INDEX = 2
const val UKRAINE_INDEX = 3

val SUPPORTED_COUNTRIES = listOf(
    Country(
        name = "Poland",
        currency = "PLN",
        currencyName = "Polish zloty",
        sendingLimit = 20000f,
        icon = R.drawable.ic_poland_big
    ),
    Country(
        name = "Germany",
        currency = "EUR",
        currencyName = "Euro",
        sendingLimit = 5000f,
        icon = R.drawable.ic_germany_big
    ),
    Country(
        name = "Great Britain",
        currency = "GBP",
        currencyName = "British Pound",
        sendingLimit = 1000f,
        icon = R.drawable.ic_uk_big
    ),
    Country(
        name = "Ukraine",
        currency = "UAH",
        currencyName = "Hrivna",
        sendingLimit = 50000f,
        icon = R.drawable.ic_ukraine_big
    )
)