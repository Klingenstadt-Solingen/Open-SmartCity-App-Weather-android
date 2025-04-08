package de.osca.android.weather.presentation.weather.lists

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import de.osca.android.essentials.presentation.component.design.BaseTextField
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper
import de.osca.android.essentials.presentation.component.topbar.ScreenTopBar
import de.osca.android.essentials.utils.extensions.SetSystemStatusBar
import de.osca.android.essentials.utils.extensions.getBounds
import de.osca.android.weather.R
import de.osca.android.weather.presentation.weather.WeatherViewModel
import kotlinx.coroutines.launch

/**

 */
@Composable
fun WeatherStationSelectionScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    masterDesignArgs: MasterDesignArgs = weatherViewModel.defaultDesignArgs,
    initialLocation: LatLng,
) {
    val design = weatherViewModel.weatherDesignArgs
    val context = LocalContext.current
    // val mDisplayMenu = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()

    val mapScrollState = rememberScrollState()
    val listScrollState = rememberScrollState()

    val searchText = remember { mutableStateOf("") }

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
                actions = {
                },
                masterDesignArgs = masterDesignArgs,
            )
        },
        screenWrapperState = weatherViewModel.wrapperState,
        retryAction = {
            weatherViewModel.initializeWeather(initialLocation)
        },
        masterDesignArgs = masterDesignArgs,
        moduleDesignArgs = design,
    ) {
        BaseCardContainer(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(horizontal = design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace),
            // text = stringResource(id = R.string),
            moduleDesignArgs = design,
            masterDesignArgs = masterDesignArgs,
        ) {
            Column {
                BaseTextField(
                    masterDesignArgs = masterDesignArgs,
                    textFieldTitle = "Suche nach Wetterstation", // stringResource(id = R.string.waste_addresses_chooseAddress),
                    onTextChange = { text ->
                        searchText.value = text
                    },
                    lineCount = 1,
                    moduleDesignArgs = design,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "gib bitte mindestens 3 Zeichen ein",
                    style = masterDesignArgs.subtitleTextStyle,
                    color = design.mHintTextColor ?: masterDesignArgs.mHintTextColor,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
            }
        }
        BaseCardContainer(
            modifier =
                Modifier.padding(
                    horizontal = design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace,
                ),
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
                            state = mapScrollState,
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
                                weatherViewModel.selectWeatherStationList
                                    .filterNotNull()
                                    .map { it.location!! },
                                cameraPositionState.position.target,
                            ),
                        )
                    }
                },
            ) {
                weatherViewModel.selectWeatherStationList.filter {
                    if (it?.name != null) {
                        it.name.contains(searchText.value, true)
                    } else {
                        false
                    }
                }.forEach { sensor ->
                    if (sensor?.location != null) {
                        Marker(
                            title = sensor.name,
                            state = MarkerState(sensor.location.toLatLng()),
                            onClick = { marker ->
                                weatherViewModel.saveWeatherStation(sensor.sourceId)
                                true
                            },
                        )
                    }
                }
            }
        }
        Column(
            modifier =
                Modifier
                    .verticalScroll(listScrollState)
                    // .background(masterDesignArgs.getWeatherGradient())
                    .fillMaxSize(),
        ) {
            weatherViewModel.selectWeatherStationList.filter {
                if (it?.name != null) {
                    it.name.contains(searchText.value, true)
                } else {
                    false
                }
            }.forEach { item ->
                if (item?.name != null) {
                    BaseCardContainer(
                        moduleDesignArgs = design,
                        masterDesignArgs = masterDesignArgs,
                        modifier =
                            Modifier.fillMaxWidth().padding(
                                horizontal =
                                    design.mRootBoarderSpacing
                                        ?: masterDesignArgs.mBorderSpace,
                                vertical = 8.dp,
                            ),
                        onClick = {
                            weatherViewModel.saveWeatherStation(
                                item.sourceId,
                            )
                        },
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = item.name,
                                style = masterDesignArgs.bodyTextStyle,
                                color = masterDesignArgs.mCardTextColor,
                                textAlign = TextAlign.Center,
                            )
                            if (weatherViewModel.favoritedWeather.value?.sourceId == item.sourceId) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_check),
                                    contentDescription = "",
                                    tint = masterDesignArgs.mCardTextColor,
                                    modifier =
                                        Modifier
                                            .requiredSize(15.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
