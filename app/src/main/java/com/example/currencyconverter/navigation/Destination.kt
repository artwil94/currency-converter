package com.example.currencyconverter.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {

    @Serializable
    data object Home : Destination()
}