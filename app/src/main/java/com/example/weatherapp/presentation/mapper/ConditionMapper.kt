package com.example.weatherapp.presentation.mapper

import com.example.weatherapp.R
import com.example.weatherapp.domain.model.WeatherCondition

fun WeatherCondition.toLocalizedStringRes(): Int {
    return when (this) {
        WeatherCondition.CLEAR -> R.string.clear_sky
        WeatherCondition.MAINLY_CLEAR -> R.string.mainly_clear
        WeatherCondition.PARTLY_CLOUDY -> R.string.partly_cloudy
        WeatherCondition.OVERCAST -> R.string.overcast
        WeatherCondition.FOG -> R.string.fog
        WeatherCondition.DRIZZLE -> R.string.drizzle
        WeatherCondition.FREEZING_DRIZZLE -> R.string.freezing_drizzle
        WeatherCondition.RAIN -> R.string.rain
        WeatherCondition.FREEZING_RAIN -> R.string.freezing_rain
        WeatherCondition.SNOW -> R.string.snow
        WeatherCondition.SNOW_GRAINS -> R.string.snow_grains
        WeatherCondition.RAIN_SHOWERS -> R.string.rain_showers
        WeatherCondition.SNOW_SHOWERS -> R.string.snow_showers
        WeatherCondition.THUNDERSTORM -> R.string.thunderstorm
        WeatherCondition.UNKNOWN -> R.string.unknown
    }
}
