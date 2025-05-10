package com.example.weatherapp

import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.BeforeTest

class WeatherRepositoryImplTest {

    private lateinit var weatherApi: WeatherApi
    private lateinit var repository: WeatherRepositoryImpl

    @BeforeTest
    fun setup() {
        weatherApi = mockk()
        repository = WeatherRepositoryImpl(weatherApi)
    }

    @Test
    fun `getCurrentWeatherByLocation should return expected weather`() = runTest {
        // Given
        val location = "30.0,31.0"
        val expected = Weather(temp = 25.0, humidity = 50, description = "Sunny", windSpeed = 12.5)
        coEvery { weatherApi.fetchCurrentWeather(location) } returns expected

        // When
        val result = repository.getCurrentWeatherByLocation(location)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getForecast should return forecast list`() = runTest {
        // Given
        val location = "30.0,31.0"
        val forecastList = listOf(
            Forecast("Mon", 24.0, 28.0, 20.0, "Sunny"),
            Forecast("Tue", 25.0, 30.0, 21.0, "Cloudy")
        )
        coEvery { weatherApi.fetchForecast(location) } returns forecastList

        // When
        val result = repository.getForecast(location)

        // Then
        assertEquals(forecastList, result)
    }
}
