package com.example.weatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherDTO(
    @SerialName("is_day") val isDay: List<Int>,
    @SerialName("temperature_2m") val temperature: List<Double>,
    @SerialName("time") val time: List<String>,
    @SerialName("weathercode") val weatherCode: List<Int>
)
