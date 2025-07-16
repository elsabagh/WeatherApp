package com.example.weatherapp.data.api

import com.example.weatherapp.core.util.Constants.CURRENT_WEATHER_PARAMS
import com.example.weatherapp.core.util.Constants.DAILY_WEATHER_PARAMS
import com.example.weatherapp.core.util.Constants.HOURLY_WEATHER_PARAMS
import com.example.weatherapp.core.util.Constants.PARAM_CURRENT
import com.example.weatherapp.core.util.Constants.PARAM_DAILY
import com.example.weatherapp.core.util.Constants.PARAM_HOURLY
import com.example.weatherapp.core.util.Constants.PARAM_LATITUDE
import com.example.weatherapp.core.util.Constants.PARAM_LONGITUDE
import com.example.weatherapp.core.util.Constants.PARAM_TIMEZONE
import com.example.weatherapp.core.util.Constants.PARAM_TIMEZONE_VALUE
import com.example.weatherapp.core.util.Constants.WEATHER_API_URL
import com.example.weatherapp.data.model.WeatherResponseDTO
import com.example.weatherapp.domain.model.UserLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Single

@Single
class WeatherApiService(
    private val client: HttpClient,
) {
    suspend fun getWeatherData(
        location: UserLocation
    ): WeatherResponseDTO {
        return client.get(WEATHER_API_URL) {
            url {
                parameters.append(PARAM_LATITUDE, location.latitude.toString())
                parameters.append(PARAM_LONGITUDE, location.longitude.toString())
                parameters.append(PARAM_CURRENT, CURRENT_WEATHER_PARAMS)
                parameters.append(PARAM_HOURLY, HOURLY_WEATHER_PARAMS)
                parameters.append(PARAM_DAILY, DAILY_WEATHER_PARAMS)
                parameters.append(PARAM_TIMEZONE, PARAM_TIMEZONE_VALUE)
            }
        }.body()
    }
}