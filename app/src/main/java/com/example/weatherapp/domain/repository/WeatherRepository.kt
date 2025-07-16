package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.UserLocation
import com.example.weatherapp.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherData(
        location: UserLocation
    ): Flow<WeatherData>
}