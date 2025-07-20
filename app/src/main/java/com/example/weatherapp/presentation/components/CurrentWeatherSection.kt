package com.example.weatherapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.mapper.toWeatherCondition
import com.example.weatherapp.domain.model.CurrentConditions
import com.example.weatherapp.presentation.mapper.toDrawableRes
import com.example.weatherapp.presentation.mapper.toLocalizedStringRes

@Composable
fun CurrentWeatherSection(
    city: String,
    current: CurrentConditions,
    maxTemp: Double,
    minTemp: Double,
) {
    val condition = current.weatherCode.toWeatherCondition()
    val iconRes = painterResource(id = condition.toDrawableRes(current.isDay))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                city,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            painter = iconRes,
            contentDescription = "Weather Icon",
            modifier = Modifier.size(96.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "${current.temperature}°C",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = condition.toLocalizedStringRes()),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "↑ ${maxTemp.toInt()}°C   ↓ ${minTemp.toInt()}°C",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
