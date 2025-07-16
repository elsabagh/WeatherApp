package com.example.weatherapp.domain.model

data class CurrentConditions(
    val windSpeed: Double,
    val temperature: Double,
    val rainProbability: Int,
    val pressure: Double,
    val uvIndex: Double,
    val humidity: Int,
    val feelsLike: Double,
    val isDay: Boolean,
    val time: String,
    val weatherCode: Int
)