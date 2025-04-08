package de.osca.android.weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.osca.android.essentials.data.client.OSCAHttpClient
import de.osca.android.weather.data.remote.WeatherApiService
import de.osca.android.weather.data.storage.WeatherStorageImpl
import de.osca.android.weather.domain.boundaries.WeatherStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {
    @Singleton
    @Provides
    fun weatherApiService(oscaHttpClient: OSCAHttpClient): WeatherApiService =
        oscaHttpClient.create(WeatherApiService::class.java)

    @Singleton
    @Provides
    fun providesWeatherStorage(@ApplicationContext context: Context): WeatherStorage {
        return WeatherStorageImpl(context)
    }
}
