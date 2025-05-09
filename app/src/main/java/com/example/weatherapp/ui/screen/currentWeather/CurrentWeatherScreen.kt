package com.example.weatherapp.ui.screen.currentWeather

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.navigation.NavigationRoutes
import com.example.weatherapp.ui.theme.colorButtonWeather
import com.example.weatherapp.util.PermissionsUtils
import com.example.weatherapp.viewmodel.CurrentWeatherViewModel
import com.example.weatherapp.viewmodel.CurrentWeatherViewModelFactory

@SuppressLint("MissingPermission")
@Composable
fun CurrentWeatherScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { WeatherRepositoryImpl(weatherApi = WeatherApi) }

    val viewModel: CurrentWeatherViewModel = viewModel(
        factory = CurrentWeatherViewModelFactory(repository)
    )
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        PermissionsUtils.handleLocationPermissionAndFetch(context) { lat, lon ->
            viewModel.loadWeatherByCoordinates(lat, lon)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

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
                state.weather?.let {
                    WeatherMainCar(
                        temperature = "${it.temp}",
                        description = it.description
                    )
                    WeatherCard(
                        itemWeather = "Humidity",
                        itemValue = "${it.humidity}%",
                        imageResource = R.drawable.humidity
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    WeatherCard(
                        itemWeather = "Wind Speed",
                        itemValue = "${it.windSpeed} km/h",
                        imageResource = R.drawable.wind
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(NavigationRoutes.FORECAST) },
            colors = ButtonDefaults.buttonColors(colorButtonWeather),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Show Next 5 Day")
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun WeatherMainCar(
    description: String,
    temperature: String,
) {
    val tempValue = temperature.toDoubleOrNull() ?: 0.0

    val imageResource = when {
        tempValue < 10 -> R.drawable.rainy
        tempValue in 10.0..20.0 -> R.drawable.cloudy
        tempValue in 21.0..30.0 -> R.drawable.partly_cloudy
        tempValue > 30 -> R.drawable.sun
        else -> R.drawable.partly_cloudy
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier
                .size(136.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "$temperature °C", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun WeatherCard(
    itemWeather: String,
    itemValue: String,
    imageResource: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
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
                modifier = Modifier
                    .size(46.dp)
            )
            Text(text = itemWeather, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = itemValue, fontSize = 14.sp)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun CurrentWeatherScreenPreview() {
    CurrentWeatherScreen(navController = NavController(LocalContext.current))
}