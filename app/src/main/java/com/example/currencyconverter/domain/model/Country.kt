package com.example.currencyconverter.domain.model

import androidx.annotation.DrawableRes

data class Country(
    val name: String,
    val currency: String,
    val currencyName: String,
    val sendingLimit: Int,
    @DrawableRes val icon: Int
)