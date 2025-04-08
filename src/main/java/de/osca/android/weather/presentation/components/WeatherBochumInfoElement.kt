package de.osca.android.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.osca.android.essentials.R
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs
import de.osca.android.essentials.presentation.component.design.advancedShadow

@Composable
fun WeatherBochumInfoElement(
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
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val cornerSize = moduleDesignArgs.mShapeCard ?: masterDesignArgs.mShapeCard
        val elevation = moduleDesignArgs.mCardElevation ?: masterDesignArgs.mCardElevation
        Card(
            shape = RoundedCornerShape(moduleDesignArgs.mShapeCard ?: masterDesignArgs.mShapeCard),
            backgroundColor = moduleDesignArgs.mCardBackColor ?: masterDesignArgs.mCardBackColor,
            elevation = elevation,
            modifier = if(onClick != null) Modifier
                .fillMaxWidth()
                .heightIn(140.dp)
                .advancedShadow(cornersRadius = cornerSize, elevation = elevation)
                .clip(RoundedCornerShape(cornerSize))
                .clickable {
                    onClick()
                }
            else
                Modifier
                    .fillMaxWidth()
                    .advancedShadow(cornersRadius = cornerSize, elevation = elevation)
                    .clip(RoundedCornerShape(cornerSize))
                    .heightIn(140.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Text(
                                    text = temperature,
                                    style = masterDesignArgs.bigTextStyle,
                                    color = moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor
                                )

                                Spacer(modifier = Modifier.width(16.dp))

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
                                            .size(40.dp)
                                    )
                                } else {
                                    Box(modifier = Modifier.size(40.dp))
                                }
                            }

                            Spacer(
                                modifier = Modifier
                                    .height(4.dp)
                            )

                            Text(
                                text = sensorStation,
                                style = masterDesignArgs.overlineTextStyle,
                                color = moduleDesignArgs.mCardTextColor ?: masterDesignArgs.mCardTextColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "${airSpeed} km/h",
                                style = masterDesignArgs.normalTextStyle,
                                color = moduleDesignArgs.mHintTextColor ?: masterDesignArgs.mHintTextColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(
                                modifier = Modifier
                                    .height(4.dp)
                            )

                            Text(
                                text = "${airPressure} hPa",
                                style = masterDesignArgs.normalTextStyle,
                                color = moduleDesignArgs.mHintTextColor ?: masterDesignArgs.mHintTextColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Text(
                        text = rainIntensity,
                        style = masterDesignArgs.normalTextStyle,
                        color = moduleDesignArgs.mHintTextColor ?: masterDesignArgs.mHintTextColor
                    )
                }
            }
        }
    }
}