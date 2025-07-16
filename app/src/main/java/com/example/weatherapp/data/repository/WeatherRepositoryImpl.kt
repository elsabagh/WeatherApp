package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.mapper.toDomain
import com.example.weatherapp.domain.model.UserLocation
import com.example.weatherapp.domain.model.WeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class WeatherRepositoryImpl(
    private val apiService: WeatherApiService,
) : WeatherRepository {

    override fun getWeatherData(
        location: UserLocation,
    ): Flow<WeatherData> = flow {
        val response = apiService.getWeatherData(location)
        emit(response.toDomain())
    }
}