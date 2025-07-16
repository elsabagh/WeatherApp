package com.example.weatherapp.data.model

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class CurrentConditionsDTO(
    @SerialName("wind_speed_10m") val windSpeed: Double,
    @SerialName("temperature_2m") val currentTemperature: Double,
    @SerialName("precipitation_probability") val rainPercentage: Int,
    @SerialName("surface_pressure") val surfacePressure: Double,
    @SerialName("uv_index") val ultravioletIndex: Double,
    @SerialName("relative_humidity_2m") val humidityPercentage: Int,
    @SerialName("apparent_temperature") val feelsLikeTemperature: Double,
    @SerialName("is_day") val isDay: Int,
    @SerialName("time") val time: String,
    @SerialName("weather_code") val weatherCode: Int
)
