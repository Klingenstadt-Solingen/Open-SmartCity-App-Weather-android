package de.osca.android.weather.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.osca.android.essentials.presentation.component.design.*
import de.osca.android.weather.R
import de.osca.android.weather.domain.entity.WeatherObserved
import de.osca.android.weather.presentation.args.WeatherDesignArgs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun WeatherCardsGrid(
    myWeather: WeatherObserved?,
    design: WeatherDesignArgs,
    weatherGridElementActions: List<(() -> Unit)?>,
    masterDesignArgs: MasterDesignArgs
) {
    val itemListA = mutableListOf<String>()
    val itemListB = mutableListOf<String>()
    val itemListC = mutableListOf<Int>()
    val itemListD = mutableListOf<String?>()

    if(myWeather?.values?.uvIndex?.value != null) {
        itemListA.add(myWeather.values.uvIndex.name ?: stringResource(id = R.string.weather_uv))
        itemListC.add(R.drawable.ic_sun)
        itemListB.add("${myWeather.values.uvIndex.value.roundToInt()} ${myWeather.values.uvIndex.unit ?: ""}")
        itemListD.add(myWeather.values.uvIndex.iconUrl)
    }
    if(myWeather?.values?.relativeHumidity?.value != null) {
        itemListA.add(myWeather.values.relativeHumidity.name ?: stringResource(id = R.string.weather_humidity))
        itemListC.add(R.drawable.ic_humidity)
        itemListB.add("${myWeather.values.relativeHumidity.value.roundToInt()} ${myWeather.values.relativeHumidity.unit ?: ""}")
        itemListD.add(myWeather.values.relativeHumidity.iconUrl)
    }
    if(myWeather?.values?.relativePressure?.value != null) {
        itemListA.add(myWeather.values.relativePressure.name ?: stringResource(id = R.string.weather_pressure))
        itemListC.add(R.drawable.ic_tachometer_light)
        itemListB.add("${myWeather.values.relativePressure.value.roundToInt()} ${myWeather.values.relativePressure.unit ?: ""}")
        itemListD.add(myWeather.values.relativePressure.iconUrl)
    }
    if(myWeather?.values?.windSpeed?.value != null) {
        itemListA.add(myWeather.values.windSpeed.name ?: stringResource(id = R.string.weather_wind_speed))
        itemListC.add(R.drawable.ic_wind_light)
        itemListB.add("${myWeather.values.windSpeed.value.roundToInt()} ${myWeather.values.windSpeed.unit ?: ""}")
        itemListD.add(myWeather.values.windSpeed.iconUrl)
    }
    if(myWeather?.values?.windDirection?.value != null) {
        itemListA.add(myWeather.values.windDirection.name ?: stringResource(id = R.string.weather_wind_direction))
        itemListC.add(R.drawable.ic_explore)
        itemListB.add(myWeather.values.windCardinalDirection?.asText() ?: stringResource(id = R.string.global_direction_unknown))
        itemListD.add(myWeather.values.windDirection.iconUrl)
    }
    if(myWeather?.values?.rainIntensity?.value != null) {
        itemListA.add(myWeather.values.rainIntensity.name ?: stringResource(id = R.string.weather_rain))
        itemListC.add(R.drawable.ic_cloud_rain_light)
        itemListB.add("${myWeather.values.rainIntensity.value.roundToInt()} ${myWeather.values.rainIntensity.unit ?: ""}")
        itemListD.add(myWeather.values.rainIntensity.iconUrl)
    }
    val formatter = SimpleDateFormat("HH:mm")
    if(myWeather?.values?.sunrise?.value != null) {
        itemListA.add(myWeather.values.sunrise.name ?: stringResource(id = R.string.weather_sunrise))
        itemListC.add(R.drawable.ic_sunrise_regular)
        itemListB.add(formatter.format(Date(myWeather.values.sunrise.value.toLong() * 1000L)))
        itemListD.add(myWeather.values.sunrise.iconUrl)
    }
    if(myWeather?.values?.sunset?.value != null) {
        itemListA.add(myWeather.values.sunset.name ?: stringResource(id = R.string.weather_sunset))
        itemListC.add(R.drawable.ic_sunset_regular)
        itemListB.add(formatter.format(Date(myWeather.values.sunset.value.toLong() * 1000L)))
        itemListD.add(myWeather.values.sunset.iconUrl)
    }
    if(myWeather?.values?.badeampel?.value != null) {
        itemListA.add(myWeather.values.badeampel.name ?: stringResource(id = R.string.weather_badeampel))
        itemListC.add(-1)
        itemListB.add(if(myWeather.values.badeampel.value <= 0) "Baden verboten" else "zum Baden geeignet")
        itemListD.add(myWeather.values.badeampel.iconUrl)
    }
    if(myWeather?.values?.wasserspiel?.value != null) {
        itemListA.add(myWeather.values.wasserspiel.name ?: stringResource(id = R.string.weather_wasserspiel))
        itemListC.add(-1)
        itemListB.add(if(myWeather.values.wasserspiel.value <= 0) "geschlossen" else "geöffnet")
        itemListD.add(myWeather.values.wasserspiel.iconUrl)
    }

    MultiColumnList(
        columnCount = 3
    ) {
        itemListA.mapIndexed { index, text ->
            { modifier ->
                WeatherInfoDetail(
                    masterDesignArgs = masterDesignArgs,
                    moduleDesignArgs = design,
                    text = text,
                    value = itemListB[index],
                    icon = itemListC[index],
                    imageUrl = itemListD[index],
                    onClick = weatherGridElementActions[index],
                    modifier = modifier
                )
            }
        }
    }
}