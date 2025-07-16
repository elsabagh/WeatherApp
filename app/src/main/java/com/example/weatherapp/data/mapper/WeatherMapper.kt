package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.model.CurrentConditionsDTO
import com.example.weatherapp.data.model.DailyWeatherDTO
import com.example.weatherapp.data.model.HourlyWeatherDTO
import com.example.weatherapp.data.model.WeatherResponseDTO
import com.example.weatherapp.domain.model.CurrentConditions
import com.example.weatherapp.domain.model.DailyWeather
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.domain.model.WeatherData


fun WeatherResponseDTO.toDomain(): WeatherData {
    return WeatherData(
        latitude = latitude,
        longitude = longitude,
        current = currentWeather.toDomain(),
        hourly = hourlyForecast.toDomain(),
        daily = dailyForecast.toDomain(),
    )
}

fun CurrentConditionsDTO.toDomain(): CurrentConditions {
    return CurrentConditions(
        windSpeed = windSpeed,
        temperature = currentTemperature,
        rainProbability = rainPercentage,
        pressure = surfacePressure,
        uvIndex = ultravioletIndex,
        humidity = humidityPercentage,
        feelsLike = feelsLikeTemperature,
        isDay = isDay == 1,
        time = time,
        weatherCode = weatherCode
    )
}

fun HourlyWeatherDTO.toDomain(): List<HourlyWeather> {
    return time.mapIndexed { index, time ->
        HourlyWeather(
            time = time,
            temperature = temperature.getOrNull(index) ?: 0.0,
            weatherCode = weatherCode.getOrNull(index) ?: 0,
            isDay = isDay.getOrNull(index) == 1
        )
    }
}

fun DailyWeatherDTO.toDomain(): List<DailyWeather> {
    return date.mapIndexed { index, date ->
        DailyWeather(
            date = date,
            minTemp = minimumTemperature.getOrNull(index) ?: 0.0,
            maxTemp = maximumTemperature.getOrNull(index) ?: 0.0,
            weatherCode = weatherCode.getOrNull(index) ?: 0
        )
    }
}