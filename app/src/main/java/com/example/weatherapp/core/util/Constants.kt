package com.example.weatherapp.core.util

object Constants {
    const val WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast"

    const val PARAM_LATITUDE = "latitude"
    const val PARAM_LONGITUDE = "longitude"
    const val PARAM_CURRENT = "current"
    const val PARAM_HOURLY = "hourly"
    const val PARAM_DAILY = "daily"
    const val PARAM_TIMEZONE = "timezone"
    const val PARAM_TIMEZONE_VALUE = "auto"

    const val CURRENT_WEATHER_PARAMS =
        "temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m," +
                "precipitation,surface_pressure,uv_index,weather_code,rain,is_day,precipitation_probability"

    const val HOURLY_WEATHER_PARAMS = "temperature_2m,weathercode,is_day"

    const val DAILY_WEATHER_PARAMS = "weather_code,temperature_2m_max,temperature_2m_min"

}