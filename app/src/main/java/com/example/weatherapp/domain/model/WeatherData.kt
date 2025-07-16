package com.example.weatherapp.domain.model

data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentConditions,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,

)
