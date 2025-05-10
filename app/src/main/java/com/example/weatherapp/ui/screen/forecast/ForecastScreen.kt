package com.example.weatherapp.ui.screen.forecast

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.ui.components.TopAppBar
import com.example.weatherapp.ui.theme.colorButtonWeather
import com.example.weatherapp.util.NetworkUtils
import com.example.weatherapp.util.PermissionsUtils
import com.example.weatherapp.viewmodel.ForecastViewModel
import com.example.weatherapp.viewmodel.ForecastViewModelFactory
import kotlinx.coroutines.delay

@SuppressLint("MissingPermission")
@Composable
fun ForecastScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val repository = WeatherRepositoryImpl(weatherApi = WeatherApi)
    val forecastViewModel: ForecastViewModel = viewModel(
        factory = ForecastViewModelFactory(repository)
    )
    val state by forecastViewModel.state.collectAsState()

    LaunchedEffect(true) {
        val connected = NetworkUtils.isNetworkAvailable(context)
        if (connected) {
            PermissionsUtils.handleLocationPermissionAndFetch(context) { lat, lon ->
                forecastViewModel.loadForecastByCoordinates(context, lat, lon)
            }
        } else {
            forecastViewModel.loadForecastByCoordinates(context, null, null)
        }
        delay(5000)
    }

    Scaffold(
        topBar = { TopAppBar("Next 5 Days", navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            when {
                state.loading ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorButtonWeather
                        )
                    }

                state.error != null -> Text("Error: ${state.error}")
                else -> {
                    state.forecastList.drop(1).forEach { forecast ->
                        DayWeatherCard(forecast)

                    }
                }
            }
        }
    }
}

@Composable
fun DayWeatherCard(forecast: Forecast) {
    val tempValue = forecast.temp

    val imageResource = when {
        tempValue < 10 -> R.drawable.rainy
        tempValue in 10.0..20.0 -> R.drawable.cloudy
        tempValue in 21.0..30.0 -> R.drawable.partly_cloudy
        tempValue > 30 -> R.drawable.sun
        else -> R.drawable.partly_cloudy
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "${forecast.temp}°C",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = forecast.date, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayWeatherCardPreview() {
    val sampleForecast = Forecast(
        temp = 25.0,
        date = "2023-10-01",
        tempMax = 35.0,
        tempMin = 20.0,
        description = "Sunny"
    )
    DayWeatherCard(forecast = sampleForecast)
}
@Preview(showBackground = true)
@Composable
fun ForecastScreenPreview() {
    ForecastScreen(navController = rememberNavController())
}
