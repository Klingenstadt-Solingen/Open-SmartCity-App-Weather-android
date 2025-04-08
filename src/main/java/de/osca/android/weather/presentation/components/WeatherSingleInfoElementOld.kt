package de.osca.android.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.osca.android.essentials.presentation.component.design.BaseCardContainer
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs

@Composable
fun WeatherSingleInfoElementOld(
    masterDesignArgs: MasterDesignArgs,
    moduleDesignArgs: ModuleDesignArgs,
    temperature: String,
    sensorStation: String,
    rainIntensity: String,
    showWeatherSymbol: Boolean = true,
    rainValue: Float = 0f,
    airPressure: Float = 0f,
    airSpeed: Float = 0f,
    uvIndex: Float = 0f,
    useDummies: Boolean = false,
    modifier: Modifier = Modifier,
    isOnlyOneItem: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        BaseCardContainer(
            masterDesignArgs = masterDesignArgs,
            moduleDesignArgs = moduleDesignArgs,
            useContentPadding = false,
            onClick = onClick
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(100.dp)
                    .background(masterDesignArgs.getWeatherGradient())
                    .padding(moduleDesignArgs.mContentPaddingForMiniCards ?: masterDesignArgs.mContentPaddingForMiniCards)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1.0f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = temperature,
                                style = masterDesignArgs.overlineTextStyle.copy(fontSize = 24.sp),
                                color = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor
                            )

                            val selectedIcon = if (rainValue <= .01f) {
                                if (uvIndex >= .9f || airPressure >= 1100) {
                                    de.osca.android.weather.R.drawable.sunny
                                } else if (uvIndex >= .3f || airPressure >= 1000) {
                                    de.osca.android.weather.R.drawable.partly_cloudy
                                } else {
                                    if (airPressure <= 700) {
                                        de.osca.android.weather.R.drawable.rain_light
                                    } else {
                                        de.osca.android.weather.R.drawable.cloudy
                                    }
                                }
                            } else if (rainValue <= .6f) {
                                de.osca.android.weather.R.drawable.rain_light
                            } else {
                                de.osca.android.weather.R.drawable.rain_s_cloudy
                            }

                            if(showWeatherSymbol) {
                                Image(
                                    painter = painterResource(id = selectedIcon),
                                    contentDescription = "weatherIcon",
                                    modifier = Modifier
                                        .size(28.dp)
                                )
                            } else {
                                Box(modifier = Modifier.size(28.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = sensorStation,
                            style = masterDesignArgs.normalTextStyle,
                            color = moduleDesignArgs.mCardBackColor
                                ?: masterDesignArgs.mCardBackColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .background(moduleDesignArgs.mHintTextColor ?: masterDesignArgs.mHintTextColor)
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .weight(1.0f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = de.osca.android.weather.R.drawable.ic_wind_light),
                                contentDescription = "tachoIcon",
                                tint = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor,
                                modifier = Modifier
                                    .size(15.dp)
                            )
                            Text(
                                text = "${airSpeed} km/h",
                                style = masterDesignArgs.normalTextStyle.copy(fontSize = 12.sp),
                                color = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = de.osca.android.weather.R.drawable.ic_tachometer_light),
                                contentDescription = "raindropIcon",
                                tint = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor,
                                modifier = Modifier
                                    .size(15.dp)
                            )
                            Text(
                                text = "${airPressure} hPa",
                                style = masterDesignArgs.normalTextStyle.copy(fontSize = 12.sp),
                                color = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = de.osca.android.weather.R.drawable.ic_weather_raindrop),
                                contentDescription = "raindropIcon",
                                tint = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor,
                                modifier = Modifier
                                    .size(15.dp)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = rainIntensity,
                                style = masterDesignArgs.normalTextStyle.copy(fontSize = 12.sp),
                                color = moduleDesignArgs.mCardBackColor
                                    ?: masterDesignArgs.mCardBackColor,
                            )
                        }
                    }
                }
            }
        }
    }
}