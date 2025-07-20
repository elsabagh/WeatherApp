package com.example.weatherapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.core.util.Result
import com.example.weatherapp.presentation.components.CurrentWeatherSection
import com.example.weatherapp.presentation.components.DailyForecastSection
import com.example.weatherapp.presentation.components.HourlyForecastSection
import com.example.weatherapp.presentation.components.LocationPermissionHandler
import com.example.weatherapp.presentation.components.WeatherStatsGrid
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel(),
) {
    val weatherState by viewModel.weatherState.collectAsState()
    val userLocation by viewModel.userLocation.collectAsState()
    var permissionHandled by remember { mutableStateOf(false) }

    LocationPermissionHandler(
        onGranted = {
            if (!permissionHandled) {
                permissionHandled = true
                viewModel.loadWeather()
            }
        },
        onDenied = {
            if (!permissionHandled) {
                permissionHandled = true
                viewModel.loadWeatherFallback()
            }
        }
    )

    when (val result = weatherState) {
        is Result.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Result.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${result.message ?: "Unknown error"}")
            }
        }

        is Result.Success -> {
            val data = result.data
            val cityName = userLocation?.cityName ?: "Your Location"

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 36.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    CurrentWeatherSection(
                        city = cityName,
                        current = data.current,
                        maxTemp = data.daily.firstOrNull()?.maxTemp ?: 0.0,
                        minTemp = data.daily.firstOrNull()?.minTemp ?: 0.0
                    )

                    WeatherStatsGrid(current = data.current)

                    HourlyForecastSection(hourlyList = data.hourly)

                    DailyForecastSection(dailyList = data.daily)
                }
            }
        }
    }
}
