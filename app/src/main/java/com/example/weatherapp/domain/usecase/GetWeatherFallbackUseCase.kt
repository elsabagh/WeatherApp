package com.example.weatherapp.domain.usecase

import com.example.weatherapp.core.util.Result
import com.example.weatherapp.domain.model.UserLocation
import com.example.weatherapp.domain.model.WeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetWeatherFallbackUseCase(
    private val repository: WeatherRepository,
) {
    operator fun invoke(fallbackLocation: UserLocation): Flow<Result<WeatherData>> = flow {
        emit(Result.Loading)
        repository.getWeatherData(fallbackLocation)
            .catch { emit(Result.Error(it.message, it)) }
            .collect { data -> emit(Result.Success(data)) }
    }
}