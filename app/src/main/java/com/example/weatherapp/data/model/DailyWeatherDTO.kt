package com.example.weatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherDTO(
    @SerialName("temperature_2m_max") val maximumTemperature: List<Double>,
    @SerialName("temperature_2m_min") val minimumTemperature: List<Double>,
    @SerialName("time") val date: List<String>,
    @SerialName("weather_code") val weatherCode: List<Int>
)