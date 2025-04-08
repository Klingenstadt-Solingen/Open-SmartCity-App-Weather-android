package de.osca.android.weather.data.repository

import de.osca.android.networkservice.utils.RequestHandler
import de.osca.android.weather.data.remote.WeatherApiService
import de.osca.android.weather.domain.boundaries.WeatherRepository
import de.osca.android.weather.domain.boundaries.WeatherStorage
import de.osca.android.weather.domain.entity.WeatherObserved
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val apiService: WeatherApiService,
        private val requestHandler: RequestHandler,
        private val weatherStorage: WeatherStorage,
    ) : WeatherRepository {
        override suspend fun getWeatherStationId(): String? = weatherStorage.weatherSensorId.firstOrNull()

        override suspend fun getWeatherObserved(): List<WeatherObserved> {
            return requestHandler.makeRequest(apiService::getWeatherObserved) ?: emptyList()
        }

        override suspend fun getMyWeather(): WeatherObserved? {
            var currentStationId = getWeatherStationId()
            val weather = getWeatherObserved()

            if (currentStationId == null && weather.isNotEmpty()) {
                currentStationId = weather.first().sourceId
                saveWeatherStationId(currentStationId)
            }

            return weather.firstOrNull { it.sourceId == currentStationId } ?: weather.firstOrNull()
        }

        override suspend fun saveWeatherStationId(newId: String) {
            weatherStorage.saveWeatherSensorId(newId = newId)
        }

        override suspend fun getWeatherById(objectId: String): WeatherObserved? {
            return requestHandler.makeRequest {
                apiService.getWeatherById(objectId)
            }
        }
    }
