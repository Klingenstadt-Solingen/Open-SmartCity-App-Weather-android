package de.osca.android.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.osca.android.weather.R
import de.osca.android.essentials.presentation.component.design.BaseCardContainer
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs
import de.osca.android.essentials.presentation.component.design.advancedShadow

@Composable
fun WeatherMiniInfoElement(
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
            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn(100.dp)
                .padding(moduleDesignArgs.mContentPaddingForMiniCards ?: masterDesignArgs.mContentPaddingForMiniCards)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = if (isOnlyOneItem && !useDummies)
                            Modifier.fillMaxWidth()
                        else
                            Modifier
                                .weight(.3f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = if (isOnlyOneItem && !useDummies) Arrangement.Start else Arrangement.SpaceBetween,
                                modifier = if (isOnlyOneItem && !useDummies)
                                    Modifier
                                else
                                    Modifier
                                        .fillMaxWidth()
                            ) {
                                Text(
                                    text = temperature,
                                    style = masterDesignArgs.overlineTextStyle,
                                    color = moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor
                                )

                                if (isOnlyOneItem && !useDummies) {
                                    Spacer(modifier = Modifier.width(16.dp))
                                }

                                val selectedIcon = if (rainValue <= .01f) {
                                    if (uvIndex >= .9f || airPressure >= 1100) {
                                        R.drawable.sunny
                                    } else if (uvIndex >= .3f || airPressure >= 1000) {
                                        R.drawable.partly_cloudy
                                    } else {
                                        if (airPressure <= 700) {
                                            R.drawable.rain_light
                                        } else {
                                            R.drawable.cloudy
                                        }
                                    }
                                } else if (rainValue <= .6f) {
                                    R.drawable.rain_light
                                } else {
                                    R.drawable.rain_s_cloudy
                                }

                                if(showWeatherSymbol) {
                                    Image(
                                        painter = painterResource(id = selectedIcon),
                                        contentDescription = "weatherIcon",
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                } else {
                                    Box(modifier = Modifier.size(20.dp))
                                }
                            }

                            if (isOnlyOneItem && !useDummies) {
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_wind_light),
                                            contentDescription = "tachoIcon",
                                            tint = moduleDesignArgs.mCardTextColor
                                                ?: masterDesignArgs.mCardTextColor,
                                            modifier = Modifier
                                                .size(10.dp)
                                        )
                                        Text(
                                            text = "${airSpeed} km/h",
                                            style = masterDesignArgs.subtitleTextStyle,
                                            color = moduleDesignArgs.mCardTextColor
                                                ?: masterDesignArgs.mCardTextColor,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Spacer(
                                        modifier = Modifier
                                            .height(4.dp)
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_tachometer_light),
                                            contentDescription = "raindropIcon",
                                            tint = moduleDesignArgs.mCardTextColor
                                                ?: masterDesignArgs.mCardTextColor,
                                            modifier = Modifier
                                                .size(10.dp)
                                        )
                                        Text(
                                            text = "${airPressure} hPa",
                                            style = masterDesignArgs.subtitleTextStyle,
                                            color = moduleDesignArgs.mCardTextColor
                                                ?: masterDesignArgs.mCardTextColor,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = sensorStation,
                            style = masterDesignArgs.subtitleTextStyle,
                            color = moduleDesignArgs.mCardTextColor
                                ?: masterDesignArgs.mCardTextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_weather_raindrop),
                            contentDescription = "raindropIcon",
                            tint = moduleDesignArgs.mCardTextColor
                                ?: masterDesignArgs.mCardTextColor,
                            modifier = Modifier
                                .size(10.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = rainIntensity,
                            style = masterDesignArgs.subtitleTextStyle,
                            color = moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor
                        )
                    }
                }
            }
        }
    }
}