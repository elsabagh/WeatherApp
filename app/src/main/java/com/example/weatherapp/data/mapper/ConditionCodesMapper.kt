package com.example.weatherapp.data.mapper

import com.example.weatherapp.domain.model.WeatherCondition

fun Int.toWeatherCondition(): WeatherCondition = when (this) {
    0 -> WeatherCondition.CLEAR
    1 -> WeatherCondition.MAINLY_CLEAR
    2 -> WeatherCondition.PARTLY_CLOUDY
    3 -> WeatherCondition.OVERCAST
    in 45..48 -> WeatherCondition.FOG
    in 51..67 -> WeatherCondition.DRIZZLE
    in 66..67 -> WeatherCondition.FREEZING_DRIZZLE
    in 61..65 -> WeatherCondition.RAIN
    in 80..82 -> WeatherCondition.RAIN_SHOWERS
    in 95..99 -> WeatherCondition.THUNDERSTORM
    in 71..77 -> WeatherCondition.SNOW
    85, 86 -> WeatherCondition.SNOW_SHOWERS
    else -> WeatherCondition.UNKNOWN
}