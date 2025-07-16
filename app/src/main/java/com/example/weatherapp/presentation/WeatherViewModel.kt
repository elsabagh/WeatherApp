package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.util.Result
import com.example.weatherapp.domain.model.UserLocation
import com.example.weatherapp.domain.model.WeatherData
import com.example.weatherapp.domain.usecase.GetWeatherFallbackUseCase
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherFallbackUseCase: GetWeatherFallbackUseCase,
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<WeatherData>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherData>> = _weatherState

    private val _userLocation = MutableStateFlow<UserLocation?>(null)
    val userLocation: StateFlow<UserLocation?> = _userLocation

    fun loadWeather() {
        viewModelScope.launch {
            getWeatherUseCase().collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _weatherState.value = Result.Loading
                    }
                    is Result.Success -> {
                        val (location, weatherData) = result.data
                        _userLocation.value = location
                        _weatherState.value = Result.Success(weatherData)
                    }
                    is Result.Error -> {
                        _weatherState.value = Result.Error(result.message)
                    }
                }
            }
        }
    }
    fun loadWeatherFallback() {
        val fallback = UserLocation(
            latitude = 30.0444,
            longitude = 31.2357,
            cityName = "Cairo"
        )
        _userLocation.value = fallback
        viewModelScope.launch {
            getWeatherFallbackUseCase(fallback).collectLatest { result ->
                _weatherState.value = result
            }
        }
    }

}