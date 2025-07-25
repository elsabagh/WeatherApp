package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavigationRoutes.CURRENT_WEATHER) {
//        composable(NavigationRoutes.CURRENT_WEATHER) {
//            CurrentWeatherScreen(navController)
//        }
//        composable(NavigationRoutes.FORECAST) {
//            ForecastScreen(navController)
//        }
    }
}