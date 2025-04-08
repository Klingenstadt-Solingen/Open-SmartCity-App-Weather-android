package de.osca.android.weather.domain.boundaries

import kotlinx.coroutines.flow.Flow

interface WeatherStorage {
    val weatherSensorId : Flow<String?>
    suspend fun saveWeatherSensorId(newId: String)
}