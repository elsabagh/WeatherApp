package com.example.weatherapp.domain.model

data class HourlyWeather(
    val time: String,
    val temperature: Double,
    val weatherCode: Int,
    val isDay: Boolean
)