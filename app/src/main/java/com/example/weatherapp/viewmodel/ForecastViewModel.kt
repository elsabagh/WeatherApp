package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.CacheManager
import com.example.weatherapp.domain.exception.CacheReadException
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

    fun loadForecastByCoordinates(context: Context, lat: Double?, lon: Double?) {
        _state.value = ForecastState(loading = true)
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

                val forecast = weatherRepository.getForecast(location)
                CacheManager.saveForecast(context, forecast)
                _state.value = ForecastState(forecastList = forecast)

            } catch (_: Exception) {
                try {
                    val cachedForecast = CacheManager.getCachedForecast(context)
                    _state.value = ForecastState(forecastList = cachedForecast)
                } catch (ce: CacheReadException) {
                    _state.value = ForecastState(error = ce.message)
                }
            }

        }
    }
}
