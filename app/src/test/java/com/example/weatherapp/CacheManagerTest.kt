package com.example.weatherapp

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.data.local.CacheManager
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.domain.exception.CacheReadException
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class CacheManagerTest {

    private lateinit var context: Context
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @BeforeTest
    fun setUp() {
        context = mockk()
        sharedPrefs = mockk()
        editor = mockk()

        every { context.getSharedPreferences(any(), any()) } returns sharedPrefs
        every { sharedPrefs.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.apply() } just Runs
    }

    @Test
    fun `saveWeather should store formatted string`() {
        // Given
        val weather = Weather(25.0, 50, "Sunny", 10.5)

        // When
        CacheManager.saveWeather(context, weather)

        // Then
        verify {
            editor.putString("cached_weather", "25.0,50,Sunny,10.5")
            editor.apply()
        }
    }

    @Test
    fun `getCachedWeather should return valid weather object`() {
        // Given
        every { sharedPrefs.getString("cached_weather", null) } returns "26.5,40,Cloudy,15.2"

        // When
        val result = CacheManager.getCachedWeather(context)

        // Then
        val expected = Weather(26.5, 40, "Cloudy", 15.2)
        assertEquals(expected, result)
    }

    @Test
    fun `getCachedWeather should throw exception for malformed data`() {
        // Given
        every { sharedPrefs.getString("cached_weather", null) } returns "wrong,data"

        // When + Then
        assertFailsWith<CacheReadException> {
            CacheManager.getCachedWeather(context)
        }
    }

    @Test
    fun `getCachedWeather should throw if no data found`() {
        // Given
        every { sharedPrefs.getString("cached_weather", null) } returns null

        // When + Then
        assertFailsWith<CacheReadException> {
            CacheManager.getCachedWeather(context)
        }
    }

    // Forecast Tests
    @Test
    fun `saveForecast should store serialized string`() {
        // Given
        val forecastList = listOf(
            Forecast("Mon", 25.0, 30.0, 20.0, "Clear"),
            Forecast("Tue", 26.0, 31.0, 21.0, "Rain")
        )

        // When
        CacheManager.saveForecast(context, forecastList)

        // Then
        verify {
            editor.putString(
                "cached_forecast",
                match { it.contains("Clear") && it.contains("Rain") })
            editor.apply()
        }
    }

    @Test
    fun `getCachedForecast should parse stored forecast list`() {
        // Given
        val value = "Mon,25.0,30.0,20.0,Clear;Tue,26.0,31.0,21.0,Rain"
        every { sharedPrefs.getString("cached_forecast", null) } returns value

        // When
        val list = CacheManager.getCachedForecast(context)

        val expected = listOf(
            Forecast("Mon", 25.0, 30.0, 20.0, "Clear"),
            Forecast("Tue", 26.0, 31.0, 21.0, "Rain")
        )
        // Then
        assertEquals(expected, list)
    }

    @Test
    fun `getCachedForecast should throw if malformed`() {
        // Given
        every { sharedPrefs.getString("cached_forecast", null) } returns "bad-data,only"

        // When + Then
        assertFailsWith<CacheReadException> {
            CacheManager.getCachedForecast(context)
        }
    }

    @Test
    fun `getCachedForecast should throw if no data found`() {
        // Given
        every { sharedPrefs.getString("cached_forecast", null) } returns null

        // When + Then
        assertFailsWith<CacheReadException> {
            CacheManager.getCachedForecast(context)
        }
    }

    @Test
    fun `saveCoordinates should store latitude and longitude as strings`() {
        // When
        CacheManager.saveCoordinates(context, 30.5, 31.2)

        // Then
        verify {
            editor.putString("cached_lat", "30.5")
            editor.putString("cached_lon", "31.2")
            editor.apply()
        }
    }

    @Test
    fun `getCachedCoordinates should return correct pair`() {
        // Given
        every { sharedPrefs.getString("cached_lat", null) } returns "29.1"
        every { sharedPrefs.getString("cached_lon", null) } returns "31.0"

        // When
        val result = CacheManager.getCachedCoordinates(context)

        // Then
        assertEquals(Pair(29.1, 31.0), result)
    }

    @Test
    fun `getCachedCoordinates should return null if lat is null`() {
        // Given
        every { sharedPrefs.getString("cached_lat", null) } returns null
        every { sharedPrefs.getString("cached_lon", null) } returns "31.9"

        // When
        val result = CacheManager.getCachedCoordinates(context)

        // Then
        assertNull(result)
    }

    @Test
    fun `getCachedCoordinates should return null if lon is null`() {
        // Given
        every { sharedPrefs.getString("cached_lat", null) } returns "30.1"
        every { sharedPrefs.getString("cached_lon", null) } returns null

        // When
        val result = CacheManager.getCachedCoordinates(context)

        // Then
        assertNull(result)
    }

    @Test
    fun `getCachedCoordinates should return null if lat is not a number`() {
        // Given
        every { sharedPrefs.getString("cached_lat", null) } returns "not_a_number"
        every { sharedPrefs.getString("cached_lon", null) } returns "30.0"

        // When
        val result = CacheManager.getCachedCoordinates(context)

        // Then
        assertNull(result)
    }
}

