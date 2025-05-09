package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.ui.screen.currentWeather.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    fun loadWeatherByCity(city: String) {
        _state.value = WeatherState(loading = true)
        viewModelScope.launch {
            try {
                val weather = weatherRepository.getCurrentWeatherByLocation(city)
                _state.value = WeatherState(weather = weather)
            } catch (e: Exception) {
                _state.value = WeatherState(error = e.message)
            }
        }
    }

    fun loadWeatherByCoordinates(lat: Double, lon: Double) {
        val location = "$lat,$lon"
        loadWeatherByCity(location)
    }
}
