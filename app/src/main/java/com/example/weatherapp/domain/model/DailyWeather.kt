package com.example.weatherapp.domain.model

data class DailyWeather(
    val date: String,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherCode: Int
)