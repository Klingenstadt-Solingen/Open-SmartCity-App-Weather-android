package de.osca.android.weather.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.osca.android.weather.data.repository.WeatherRepositoryImpl
import de.osca.android.weather.domain.boundaries.WeatherRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepositoryModule {
    @Binds
    abstract fun provideWeatherRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}