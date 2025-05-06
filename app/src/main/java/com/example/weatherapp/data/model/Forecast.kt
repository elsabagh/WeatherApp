package com.example.weatherapp.data.model

data class Forecast(
    val date: String,
    val tempMax: Double,
    val tempMin: Double,
    val description: String
)