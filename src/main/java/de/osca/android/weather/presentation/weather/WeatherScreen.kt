package de.osca.android.weather.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import de.osca.android.essentials.presentation.component.design.RootContainer
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper
import de.osca.android.essentials.presentation.component.topbar.ScreenTopBar
import de.osca.android.essentials.utils.extensions.SetSystemStatusBar
import de.osca.android.weather.presentation.components.SectionCurrentTemperature
import de.osca.android.weather.presentation.components.WeatherCardsGrid

/**
 * @param sensorId
 * @param isMocked
 */
@Composable
fun WeatherScreen(
    navController: NavController,
    objectId: String?,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    weatherGridElementActions: List<(() -> Unit)?>,
    isMocked: Boolean = false,
    masterDesignArgs: MasterDesignArgs = weatherViewModel.defaultDesignArgs,
) {
    val design = weatherViewModel.weatherDesignArgs
    val mDisplayMenu = remember { mutableStateOf(false) }

    LaunchedEffect(objectId) {
        weatherViewModel.initializeWeather()
        weatherViewModel.initWeatherDetail(objectId)
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
                    IconButton(
                        onClick = {
                            mDisplayMenu.value = !mDisplayMenu.value
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "menu",
                            tint = design.mTopBarTextColor ?: masterDesignArgs.mTopBarTextColor,
                        )
                    }

                    DropdownMenu(
                        expanded = mDisplayMenu.value,
                        onDismissRequest = {
                            mDisplayMenu.value = false
                        },
                        modifier =
                            Modifier
                                .background(
                                    design.mMenuBackColor ?: masterDesignArgs.mMenuBackColor,
                                ),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                weatherViewModel.saveWeatherStation(
                                    weatherViewModel.selectedWeather.value?.sourceId ?: "",
                                )
                                mDisplayMenu.value = false
                            },
                        ) {
                            Text(
                                text = "Als meine Station festlegen",
                                style = masterDesignArgs.normalTextStyle,
                                color =
                                    design.mMenuBackColor
                                        ?: masterDesignArgs.mMenuTextColor,
                            )
                        }
                    }

                },
                masterDesignArgs = masterDesignArgs,
            )
        },
        screenWrapperState = weatherViewModel.detailWrapperState,
        retryAction = {
            weatherViewModel.initWeatherDetail(objectId)
        },
        masterDesignArgs = masterDesignArgs,
        moduleDesignArgs = design,
    ) {
        Column(
            modifier =
                Modifier
                    .background(masterDesignArgs.getWeatherGradient())
                    .fillMaxHeight(),
        ) {
            RootContainer(
                masterDesignArgs = masterDesignArgs,
                moduleDesignArgs = design,
            ) {
                item {
                    SectionCurrentTemperature(
                        myWeather = weatherViewModel.selectedWeather.value,
                        masterDesignArgs = masterDesignArgs,
                        design = design,
                    )
                }

                item {
                    WeatherCardsGrid(
                        myWeather = weatherViewModel.selectedWeather.value,
                        design = design,
                        weatherGridElementActions = weatherGridElementActions,
                        masterDesignArgs = masterDesignArgs,
                    )
                }
            }
        }
    }
}
