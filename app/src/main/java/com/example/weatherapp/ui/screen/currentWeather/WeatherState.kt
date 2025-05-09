package com.example.weatherapp.ui.screen.currentWeather

import com.example.weatherapp.data.model.Weather

data class WeatherState(
    val weather: Weather? = null,
    val loading: Boolean = false,
    val error: String? = null,
)