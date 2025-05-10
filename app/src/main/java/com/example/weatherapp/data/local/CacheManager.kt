package com.example.weatherapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.domain.exception.CacheReadException

object CacheManager {

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveWeather(context: Context, weather: Weather) {
        val weatherString =
            "${weather.temp},${weather.humidity},${weather.description},${weather.windSpeed}"
        prefs(context).edit { putString(KEY_WEATHER, weatherString) }
    }

    fun getCachedWeather(context: Context): Weather {
        val weatherString = prefs(context).getString(KEY_WEATHER, null)
            ?: throw CacheReadException("No cached weather data found.")

        val parts = weatherString.split(",")
        if (parts.size != 4) throw CacheReadException("Malformed weather data.")

        return try {
            Weather(
                temp = parts[0].toDouble(),
                humidity = parts[1].toInt(),
                description = parts[2],
                windSpeed = parts[3].toDouble()
            )
        } catch (e: Exception) {
            throw CacheReadException("Failed to parse cached weather: ${e.message}")
        }
    }

    fun saveForecast(context: Context, forecastList: List<Forecast>) {
        val serialized = forecastList.joinToString(";") {
            val cleanDate = it.date.replace(",", " ")
            val cleanDescription = it.description.replace(",", " ")
            "$cleanDate,${it.temp},${it.tempMax},${it.tempMin},$cleanDescription"
        }
        prefs(context).edit { putString(KEY_FORECAST, serialized) }
    }

    fun getCachedForecast(context: Context): List<Forecast> {
        val serialized = prefs(context).getString(KEY_FORECAST, null)
            ?: throw CacheReadException("No cached forecast data found.")

        return try {
            serialized.split(";").mapIndexed { index, item ->
                val parts = item.split(",")
                if (parts.size != 5) throw CacheReadException("Malformed forecast at index $index")

                Forecast(
                    date = parts[0],
                    temp = parts[1].toDouble(),
                    tempMax = parts[2].toDouble(),
                    tempMin = parts[3].toDouble(),
                    description = parts[4]
                )
            }
        } catch (e: Exception) {
            throw CacheReadException("Failed to parse forecast cache: ${e.message}")
        }
    }

    fun saveCoordinates(context: Context, lat: Double, lon: Double) {
        prefs(context).edit {
            putString(KEY_LAT, lat.toString())
                .putString(KEY_LON, lon.toString())
        }
    }

    fun getCachedCoordinates(context: Context): Pair<Double, Double>? {
        val lat = prefs(context).getString(KEY_LAT, null)?.toDoubleOrNull()
        val lon = prefs(context).getString(KEY_LON, null)?.toDoubleOrNull()
        return if (lat != null && lon != null) Pair(lat, lon) else null
    }

    private const val PREFS_NAME = "weather_prefs"
    private const val KEY_WEATHER = "cached_weather"
    private const val KEY_FORECAST = "cached_forecast"
    private const val KEY_LAT = "cached_lat"
    private const val KEY_LON = "cached_lon"
}
