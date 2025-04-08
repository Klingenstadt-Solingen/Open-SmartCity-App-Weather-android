package de.osca.android.weather.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import de.osca.android.weather.domain.boundaries.WeatherStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherStorageImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : WeatherStorage {
    override val weatherSensorId: Flow<String?>
        get() = context.storage.data
            .map { preferences -> preferences[WEATHER_STATION_ID] }

    override suspend fun saveWeatherSensorId(newId: String) {
        context.storage.edit { preferences ->
            preferences[WEATHER_STATION_ID] = newId
        }
    }

    companion object {
        const val WEATHER_PREFERENCES_NAME = "weather_preferences"
        val WEATHER_STATION_ID = stringPreferencesKey("weather_station_id")

        private val Context.storage: DataStore<Preferences> by preferencesDataStore(
            WEATHER_PREFERENCES_NAME
        )
    }
}