package de.osca.android.weather.domain.boundaries

import de.osca.android.weather.domain.entity.WeatherObserved

interface WeatherRepository {
    suspend fun getWeatherStationId(): String?

    suspend fun getWeatherObserved(): List<WeatherObserved>

    suspend fun getMyWeather(): WeatherObserved?

    suspend fun saveWeatherStationId(newId: String)

    suspend fun getWeatherById(objectId: String): WeatherObserved?
}
