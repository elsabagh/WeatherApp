package com.example.weatherapp.ui.screen.forecast

import com.example.weatherapp.data.model.Forecast

data class ForecastState(
    val forecastList: List<Forecast> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)