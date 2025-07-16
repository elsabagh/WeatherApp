package com.example.weatherapp.data.model

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDTO(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("current") val currentWeather: CurrentConditionsDTO,
    @SerialName("daily") val dailyForecast: DailyWeatherDTO,
    @SerialName("hourly") val hourlyForecast: HourlyWeatherDTO
)


