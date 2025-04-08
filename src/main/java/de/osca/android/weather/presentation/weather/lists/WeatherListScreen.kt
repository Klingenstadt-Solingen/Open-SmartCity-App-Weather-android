package de.osca.android.weather.presentation.weather.lists

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import de.osca.android.essentials.presentation.component.design.BaseCardContainer
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.MultiColumnList
import de.osca.android.essentials.presentation.component.design.RootContainer
import de.osca.android.essentials.presentation.component.design.SimpleSpacedList
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper
import de.osca.android.essentials.presentation.component.topbar.ScreenTopBar
import de.osca.android.essentials.utils.extensions.SetSystemStatusBar
import de.osca.android.essentials.utils.extensions.getBounds
import de.osca.android.essentials.utils.extensions.toFormattedString
import de.osca.android.weather.navigation.WeatherNavItems
import de.osca.android.weather.presentation.components.WeatherMiniInfoElement
import de.osca.android.weather.presentation.weather.WeatherViewModel
import kotlinx.coroutines.launch

/**
 * List of all Weather Stations
 * 3 Weather Info Card in Row
 * Map with all stations at the top
 * Click on Marker opens InfoBox and Click on InfoBox opens Weather-Module
 * Sorted by nearest Weather Stations from users location
 * @param isMocked whether the data is coming from Parse or use mocked data
 * @param initialLocation with which location the Widget should egt initialized
 */
@Composable
fun WeatherListScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    masterDesignArgs: MasterDesignArgs = weatherViewModel.defaultDesignArgs,
    isMocked: Boolean = false,
    initialLocation: LatLng,
) {
    val context = LocalContext.current
    val design = weatherViewModel.weatherDesignArgs

    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        weatherViewModel.initializeWeather(initialLocation)
    }

    SetSystemStatusBar(
        !(design.mIsStatusBarWhite ?: masterDesignArgs.mIsStatusBarWhite),
        Color.Transparent,
    )

    ScreenWrapper(
        topBar = {
            ScreenTopBar(
                title = stringResource(id = design.vModuleTitle),
                navController = navController,
                overrideTextColor = design.mTopBarTextColor,
                overrideBackgroundColor = design.mTopBarBackColor,
                masterDesignArgs = masterDesignArgs,
            )
        },
        screenWrapperState = weatherViewModel.wrapperState,
        masterDesignArgs = masterDesignArgs,
        moduleDesignArgs = design,
    ) {
        RootContainer(
            masterDesignArgs = masterDesignArgs,
            moduleDesignArgs = design,
        ) {
            item {
                SimpleSpacedList(
                    masterDesignArgs = masterDesignArgs,
                ) {
                    BaseCardContainer(
                        moduleDesignArgs = design,
                        useContentPadding = false,
                        overrideConstraintHeight = design.mapCardHeight,
                        masterDesignArgs = masterDesignArgs,
                    ) {
                        GoogleMap(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    // the modifier takes the horizontal scroll input and blocks those events from reaching the GoogleMap.
                                    // Therefor, this disables the horizontal movement on the map but allows scrolling on the screen and and while using zoom gestures on the map
                                    .scrollable(
                                        state = scrollState,
                                        orientation = Orientation.Horizontal,
                                    ),
                            cameraPositionState = cameraPositionState,
                            properties =
                                MapProperties(
                                    mapStyleOptions =
                                        if (design.mapStyle != null) {
                                            MapStyleOptions.loadRawResourceStyle(
                                                context,
                                                design.mapStyle!!,
                                            )
                                        } else {
                                            null
                                        },
                                ),
                            uiSettings =
                                MapUiSettings(
                                    compassEnabled = false,
                                    mapToolbarEnabled = false,
                                    indoorLevelPickerEnabled = false,
                                    myLocationButtonEnabled = false,
                                    zoomControlsEnabled = false,
                                    tiltGesturesEnabled = false,
                                    scrollGesturesEnabled = true,
                                    scrollGesturesEnabledDuringRotateOrZoom = true,
                                ),
                            onMapLoaded = {
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        getBounds(
                                            weatherViewModel.weatherInfo
                                                .filterNotNull()
                                                .map { it.location!! },
                                            cameraPositionState.position.target,
                                        ),
                                    )
                                }
                            },
                        ) {
                            weatherViewModel.weatherInfo.forEach { sensor ->
                                if (sensor?.location != null) {
                                    Marker(
                                        title = sensor.name,
                                        state = MarkerState(sensor.location.toLatLng()),
                                        onClick = { marker ->
                                            /*
                                            coroutineScope.launch {
                                                cameraPositionState.animate(
                                                    CameraUpdateFactory.newLatLngZoom(
                                                        marker.position,
                                                        15.0f
                                                    )
                                                )
                                            }
                                             */
                                            navController.navigate(
                                                WeatherNavItems.getWeatherRoute(sensor.objectId),
                                            )

                                            true
                                        },
                                    )
                                }
                            }
                        }
                    }

                    MultiColumnList(
                        columnCount = design.columnCount,
                    ) {
                        weatherViewModel.weatherInfo.map { weather ->
                            { modifier ->
                                val unit = weather?.values?.temperature?.unit ?: "°C"
                                val temp =
                                    weather
                                        ?.values
                                        ?.temperature
                                        ?.value
                                        ?.toFormattedString(1)
                                        ?.replace(",", ".")
                                WeatherMiniInfoElement(
                                    masterDesignArgs = masterDesignArgs,
                                    moduleDesignArgs = design,
                                    temperature = "$temp$unit",
                                    sensorStation = "${weather?.name}",
                                    showWeatherSymbol = design.showWeatherSymbol,
                                    rainIntensity = "${weather?.values?.rainIntensity?.value}${weather?.values?.rainIntensity?.unit}",
                                    airPressure = weather?.values?.relativePressure?.value ?: 0f,
                                    airSpeed = weather?.values?.windSpeed?.value ?: 0f,
                                    uvIndex = weather?.values?.uvIndex?.value ?: 0f,
                                    isOnlyOneItem = weatherViewModel.weatherInfo.size == 1,
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
