package com.example.weatherapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.CurrentConditions

@Composable
fun WeatherStatsGrid(current: CurrentConditions) {
    Column(modifier = Modifier.padding(8.dp)) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 12.dp, end = 12.dp),
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            StatCard(
                icon = R.drawable.ic_fast_wind,
                value = "${current.windSpeed.toInt()} km/h",
                label = "Wind",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = R.drawable.ic_humidity,
                value = "${current.humidity}%",
                label = "Humidity",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = R.drawable.ic_rain,
                value = "${current.rainProbability}%",
                label = "Rain",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = R.drawable.ic_uv_index,
                value = "${current.uvIndex}",
                label = "UV Index",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = R.drawable.ic_pressure,
                value = "${current.pressure.toInt()} hPa",
                label = "Pressure",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = R.drawable.ic_temperature,
                value = "${current.feelsLike.toInt()}°C",
                label = "Feels Like",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(icon: Int, value: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = label,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                letterSpacing = 0.25.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
