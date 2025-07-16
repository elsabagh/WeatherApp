package com.example.weatherapp.presentation.mapper

import com.example.weatherapp.R
import com.example.weatherapp.domain.model.WeatherCondition

fun WeatherCondition.toDrawableRes(isDay: Boolean): Int {
    return when (this) {
        WeatherCondition.CLEAR -> if (isDay) R.drawable.light_clear_sky else R.drawable.night_clear_sky
        WeatherCondition.MAINLY_CLEAR -> if (isDay) R.drawable.light_mainly_clear else R.drawable.night_mainly_clear
        WeatherCondition.PARTLY_CLOUDY -> if (isDay) R.drawable.light_partialy_cloudy else R.drawable.night_partly_cloudy
        WeatherCondition.OVERCAST -> if (isDay) R.drawable.light_overcast else R.drawable.night_overcast
        WeatherCondition.FOG -> if (isDay) R.drawable.light_fog else R.drawable.night_fog
        WeatherCondition.DRIZZLE -> if (isDay) R.drawable.light_drizzle_light else R.drawable.night_drizzle_light
        WeatherCondition.FREEZING_DRIZZLE -> if (isDay) R.drawable.light_freezing_drizzle_light else R.drawable.night_freezing_drizzle_light
        WeatherCondition.RAIN -> if (isDay) R.drawable.light_rain_moderate else R.drawable.night_rain_moderate
        WeatherCondition.FREEZING_RAIN -> if (isDay) R.drawable.light_freezing_rain_light else R.drawable.night_freezing_rain_light
        WeatherCondition.SNOW -> if (isDay) R.drawable.light_snow_fall_moderate else R.drawable.night_snowfall_moderate
        WeatherCondition.SNOW_GRAINS -> if (isDay) R.drawable.light_snow_grains else R.drawable.night_snow_grains
        WeatherCondition.THUNDERSTORM -> if (isDay) R.drawable.light_thunderstorm_with_slight_hail else R.drawable.night_thunderstorm_with_slight_hail
        else -> R.drawable.light_mainly_clear
    }
}
