package de.osca.android.weather.presentation.args

import androidx.compose.ui.unit.Dp
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs
import de.osca.android.essentials.presentation.component.design.WidgetDesignArgs

interface WeatherDesignArgs : ModuleDesignArgs, WidgetDesignArgs {
    val columnCount: Int
    val mapCardHeight: Dp
    val showRainDropIcon: Boolean
    val showRainIntensity: Boolean
    val showWeatherSymbol: Boolean
    val fillDummies: Boolean
    val mapZoomLevel: Float
    val widgetTitleAppending: Int?
    val weatherDigits: Int
    val mapStyle: Int?
}