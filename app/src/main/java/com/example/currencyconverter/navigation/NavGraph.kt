package com.example.currencyconverter.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencyconverter.home.HomeScreen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Destination.Home) {
        composable<Destination.Home> {
            HomeScreen()
        }
    }
}