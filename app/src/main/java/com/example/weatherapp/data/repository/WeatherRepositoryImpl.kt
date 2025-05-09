package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
) : WeatherRepository {
    override suspend fun getCurrentWeatherByLocation(location: String): Weather {
        return weatherApi.fetchCurrentWeather(location)
    }

    override suspend fun getForecast(location: String): List<Forecast> {
        return weatherApi.fetchForecast(location)
    }
}