package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.CacheManager
import com.example.weatherapp.domain.exception.CacheReadException
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

    fun loadWeatherByCoordinates(context: Context, lat: Double?, lon: Double?) {
        _state.value = WeatherState(loading = true)
        viewModelScope.launch {
            try {
                val location = if (lat != null && lon != null) {
                    CacheManager.saveCoordinates(context, lat, lon)
                    "$lat,$lon"
                } else {
                    val cached = CacheManager.getCachedCoordinates(context)
                    if (cached == null) throw Exception("No location available")
                    "${cached.first},${cached.second}"
                }

                val weather = weatherRepository.getCurrentWeatherByLocation(location)
                CacheManager.saveWeather(context, weather)
                _state.value = WeatherState(weather = weather)

            } catch (_: Exception) {
                try {
                    val cached = CacheManager.getCachedWeather(context)
                    _state.value = WeatherState(weather = cached)
                } catch (ce: CacheReadException) {
                    _state.value = WeatherState(error = ce.message)
                }
            }

        }
    }
}