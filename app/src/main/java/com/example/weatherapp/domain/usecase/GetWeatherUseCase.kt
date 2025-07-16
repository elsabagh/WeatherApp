package com.example.weatherapp.domain.usecase

import com.example.weatherapp.core.util.Result
import com.example.weatherapp.domain.model.UserLocation
import com.example.weatherapp.domain.model.WeatherData
import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory

class GetWeatherUseCase(
    private val repository: WeatherRepository,
    private val locationRepository: LocationRepository,
) {
    operator fun invoke(): Flow<Result<Pair<UserLocation, WeatherData>>> = flow {
        emit(Result.Loading)
        try {
            val location = locationRepository.getCurrentLocation()
            repository.getWeatherData(location)
                .catch { emit(Result.Error(it.message, it)) }
                .collect { data ->
                    emit(Result.Success(location to data))
                }
        } catch (e: Exception) {
            emit(Result.Error(e.message, e))
        }
    }
}