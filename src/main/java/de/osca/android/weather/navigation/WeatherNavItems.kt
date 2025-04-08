package de.osca.android.weather.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import de.osca.android.essentials.domain.entity.navigation.NavigationItem
import de.osca.android.weather.R

sealed class WeatherNavItems {
    object WeatherNavItem : NavigationItem(
        title = R.string.weather_title,
        route = getWeatherRoute(),
        arguments =
            listOf(
                navArgument(ARG_WEATHER) {
                    type = NavType.StringType
                },
            ),
        icon = R.drawable.ic_cloud_rain_light,
        deepLinks =
            listOf(
                navDeepLink {
                    uriPattern = "solingen://sensorstation/detail?${ARG_WEATHER}={${ARG_WEATHER}}"
                },
            ),
    )

    object WeatherSelectionNavItem : NavigationItem(
        title = R.string.weather_title,
        route = "weatherSelection",
        icon = R.drawable.ic_city_weather_cloud,
    )

    object WeatherListNavItem : NavigationItem(
        title = R.string.weather_title,
        route = "weatherList",
        icon = R.drawable.ic_city_weather_cloud,
        deepLinks = listOf(navDeepLink { uriPattern = "solingen://sensorstation" }),
    )

    companion object {
        private const val WEATHER_ROUTE = "weather"
        const val ARG_WEATHER = "station"

        fun getWeatherRoute(objectId: String? = null): String {
            return if (objectId != null) {
                "$WEATHER_ROUTE?$ARG_WEATHER=$objectId"
            } else {
                "$WEATHER_ROUTE?$ARG_WEATHER={$ARG_WEATHER}"
            }
        }
    }
}
