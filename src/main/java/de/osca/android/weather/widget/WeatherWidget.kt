package de.osca.android.weather.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import de.osca.android.essentials.presentation.component.design.*
import de.osca.android.essentials.utils.extensions.safeTake
import de.osca.android.essentials.utils.extensions.toFormattedString
import de.osca.android.weather.navigation.WeatherNavItems
import de.osca.android.weather.presentation.components.WeatherBochumInfoElement
import de.osca.android.weather.presentation.components.WeatherMiniInfoElement
import de.osca.android.weather.presentation.components.WeatherSingleInfoElement
import de.osca.android.weather.presentation.components.WeatherSingleInfoElementOld
import de.osca.android.weather.presentation.weather.WeatherViewModel

/**
 * Default Widget for Weather
 * 3 Weather Info Card in Row
 * The 3 nearest Weather Stations from users location
 * @param cityName name of the city
 * @param isMocked whether the data is coming from Parse or use mocked data
 * @param initialLocation with which location the Widget should egt initialized
 */
@Composable
fun WeatherWidget(
    navController: NavController,
    @StringRes cityName: Int = -1,
    @DrawableRes iconUnderLine: Int = -1,
    underLineColor: Color = Color.White,
    isMocked: Boolean = false,
    isBochum: Boolean = false,
    useOldStyle: Boolean = false,
    initialLocation: LatLng,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    masterDesignArgs: MasterDesignArgs = weatherViewModel.defaultDesignArgs,
) {
    if (weatherViewModel.weatherDesignArgs.vIsWidgetVisible) {
        val design = weatherViewModel.weatherDesignArgs

        LaunchedEffect(Unit) {
            weatherViewModel.initializeWeather(initialLocation)
        }

        BaseListContainer(
            text = "${stringResource(id = design.vWidgetTitle)}${stringResource(id = cityName)}${
                if (design.widgetTitleAppending != null) {
                    stringResource(
                        id = design.widgetTitleAppending!!,
                    )
                } else {
                    ""
                }
            }",
            showMoreOption = design.vWidgetShowMoreOption,
            moduleDesignArgs = design,
            iconUnderLine = iconUnderLine,
            underLineColor = underLineColor,
            onMoreOptionClick = {
                navController.navigate(WeatherNavItems.WeatherListNavItem.route)
            },
            masterDesignArgs = masterDesignArgs,
        ) {
            if (weatherViewModel.isWeatherInitialized.value) {
                MultiColumnList(
                    columnCount = design.columnCount,
                ) {
                    weatherViewModel.weatherInfo
                        .safeTake(design.columnCount)
                        .map { weather ->
                            { modifier ->
                                val unit = weather?.values?.temperature?.unit ?: "°C"
                                val temp =
                                    weather?.values?.temperature?.value?.toFormattedString(1)
                                        ?.replace(",", ".")
                                if (!isBochum) {
                                    if (weatherViewModel.weatherInfo.size == 1 && !design.fillDummies) {
                                        if (!useOldStyle) {
                                            WeatherSingleInfoElement(
                                                masterDesignArgs = masterDesignArgs,
                                                moduleDesignArgs = design,
                                                temperature = "$temp $unit",
                                                sensorStation = "${weather?.shortName}",
                                                showWeatherSymbol = design.showWeatherSymbol,
                                                rainIntensity = "${weather?.values?.rainIntensity?.value} ${weather?.values?.rainIntensity?.unit}",
                                                airPressure =
                                                    weather?.values?.relativePressure?.value
                                                        ?: 0f,
                                                airSpeed = weather?.values?.windSpeed?.value ?: 0f,
                                                uvIndex = weather?.values?.uvIndex?.value ?: 0f,
                                                onClick = {
                                                    navController.navigate(
                                                        WeatherNavItems.getWeatherRoute(weather?.objectId),
                                                    )
                                                },
                                                modifier = modifier,
                                            )
                                        } else {
                                            WeatherSingleInfoElementOld(
                                                masterDesignArgs = masterDesignArgs,
                                                moduleDesignArgs = design,
                                                temperature = "$temp $unit",
                                                sensorStation = "${weather?.name}",
                                                showWeatherSymbol = design.showWeatherSymbol,
                                                rainIntensity = "${weather?.values?.rainIntensity?.value} ${weather?.values?.rainIntensity?.unit}",
                                                airPressure =
                                                    weather?.values?.relativePressure?.value
                                                        ?: 0f,
                                                airSpeed = weather?.values?.windSpeed?.value ?: 0f,
                                                uvIndex = weather?.values?.uvIndex?.value ?: 0f,
                                                isOnlyOneItem = design.columnCount == 1,
                                                useDummies = design.fillDummies,
                                                onClick = {
                                                    navController.navigate(
                                                        WeatherNavItems.getWeatherRoute(weather?.objectId),
                                                    )
                                                },
                                                modifier = modifier,
                                            )
                                        }
                                    } else {
                                        WeatherMiniInfoElement(
                                            masterDesignArgs = masterDesignArgs,
                                            moduleDesignArgs = design,
                                            temperature = "$temp $unit",
                                            sensorStation = "${weather?.name}",
                                            showWeatherSymbol = design.showWeatherSymbol,
                                            rainIntensity = "${weather?.values?.rainIntensity?.value} ${weather?.values?.rainIntensity?.unit}",
                                            airPressure =
                                                weather?.values?.relativePressure?.value
                                                    ?: 0f,
                                            airSpeed = weather?.values?.windSpeed?.value ?: 0f,
                                            uvIndex = weather?.values?.uvIndex?.value ?: 0f,
                                            isOnlyOneItem = design.columnCount == 1,
                                            useDummies = design.fillDummies,
                                            onClick = {
                                                navController.navigate(
                                                    WeatherNavItems.getWeatherRoute(weather?.objectId),
                                                )
                                            },
                                            modifier = modifier,
                                        )
                                    }
                                } else {
                                    WeatherBochumInfoElement(
                                        masterDesignArgs = masterDesignArgs,
                                        moduleDesignArgs = design,
                                        temperature = "$temp$unit",
                                        sensorStation = "${weather?.name}",
                                        showWeatherSymbol = design.showWeatherSymbol,
                                        rainIntensity = "${weather?.values?.rainIntensity?.value} ${weather?.values?.rainIntensity?.unit}",
                                        airPressure =
                                            weather?.values?.relativePressure?.value
                                                ?: 0f,
                                        airSpeed = weather?.values?.windSpeed?.value ?: 0f,
                                        uvIndex = weather?.values?.uvIndex?.value ?: 0f,
                                        onClick = {
                                            navController.navigate(
                                                WeatherNavItems.getWeatherRoute(weather?.objectId),
                                            )
                                        },
                                        modifier = modifier,
                                    )
                                }
                            }
                        }
                }
            }
        }
    }
}
