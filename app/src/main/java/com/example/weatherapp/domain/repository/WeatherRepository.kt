package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Weather

interface WeatherRepository {
    suspend fun getCurrentWeatherByLocation(location: String): Weather
    suspend fun getForecast(location: String): List<Forecast>
}