package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale

object WeatherApi {
    suspend fun fetchCurrentWeather(location: String): Weather = withContext(Dispatchers.IO) {
        val url = "$BASE_URL/$location?unitGroup=metric&key=$API_KEY&include=current"
        val json = JSONObject(URL(url).readText()).getJSONObject("currentConditions")

        Weather(
            temp = json.getDouble("temp"),
            humidity = json.getInt("humidity"),
            description = json.getString("conditions"),
            windSpeed = json.getDouble("windspeed"),
        )
    }

    suspend fun fetchForecast(location: String): List<Forecast> = withContext(Dispatchers.IO) {
        val url = "$BASE_URL/$location?unitGroup=metric&key=$API_KEY&include=days"
        val jsonArray = JSONObject(URL(url).readText()).getJSONArray("days")
        List(6) {
            val day = jsonArray.getJSONObject(it)

            val timestamp = day.getString("datetime")
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(timestamp)
            val formattedDateString =
                SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(formattedDate!!)

            Forecast(
                date = formattedDateString,
                temp = day.getDouble("temp"),
                tempMax = day.getDouble("tempmax"),
                tempMin = day.getDouble("tempmin"),
                description = day.getString("description")
            )
        }
    }

    private const val API_KEY = "MJNHYQLKGQ7Q3M42UPJSLCE6J"
    private const val BASE_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline"
}