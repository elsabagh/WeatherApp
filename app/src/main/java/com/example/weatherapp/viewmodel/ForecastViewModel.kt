package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.ui.screen.forecast.ForecastState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ForecastState())
    val state: StateFlow<ForecastState> = _state

    fun loadForecastByCity(city: String) {
        _state.value = ForecastState(loading = true)
        viewModelScope.launch {
            try {
                val forecast = weatherRepository.getForecast(city)
                _state.value = ForecastState(forecastList = forecast)
            } catch (e: Exception) {
                _state.value = ForecastState(error = e.message)
            }
        }
    }

    fun loadForecastByCoordinates(lat: Double, lon: Double) {
        val location = "$lat,$lon"
        loadForecastByCity(location)
    }
}