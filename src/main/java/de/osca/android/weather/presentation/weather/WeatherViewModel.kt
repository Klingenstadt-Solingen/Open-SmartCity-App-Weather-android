package de.osca.android.weather.presentation.weather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapperState
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import de.osca.android.essentials.utils.extensions.resetWith
import de.osca.android.essentials.utils.extensions.toCoordinates
import de.osca.android.weather.domain.boundaries.WeatherRepository
import de.osca.android.weather.domain.entity.WeatherObserved
import de.osca.android.weather.presentation.args.WeatherDesignArgs
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
    @Inject
    constructor(
        val weatherDesignArgs: WeatherDesignArgs,
        val weatherRepository: WeatherRepository,
    ) : BaseViewModel() {
        val selectedWeather = mutableStateOf<WeatherObserved?>(null)
        val weatherInfo = mutableStateListOf<WeatherObserved?>(null)
        val favoritedWeather = mutableStateOf<WeatherObserved?>(null)

        val selectWeatherStationList = mutableStateListOf<WeatherObserved?>(null)

        val isWeatherInitialized = mutableStateOf(false)

        var detailWrapperState: MutableState<ScreenWrapperState> =
            mutableStateOf(ScreenWrapperState.WaitingInitialization)

        fun initializeWeather(location: LatLng? = null) {
            viewModelScope.launch {
                wrapperState.loading()
                val weatherObserved = async { fetchWeatherObserved(location).join() }
                awaitAll(weatherObserved)
            }
        }

        fun fetchWeatherObserved(location: LatLng?): Job =
            launchDataLoad {
                val result = weatherRepository.getWeatherObserved()
                weatherInfo.resetWith(result.filter { it.values.isValid })
                selectWeatherStationList.resetWith(result.filter { it.values.isValid })
                if (location != null) {
                    weatherInfo.sortBy {
                        location.toCoordinates().distanceTo(it?.location)
                    }
                    selectWeatherStationList.sortBy {
                        location.toCoordinates().distanceTo(it?.location)
                    }
                }

                val theId = weatherRepository.getWeatherStationId()

                if (theId != null) {
                    val weatherObserved = weatherInfo.firstOrNull { it?.sourceId == theId }
                    weatherInfo.removeIf {
                        it?.sourceId === weatherObserved?.sourceId
                    }
                    weatherInfo.add(0, weatherObserved)
                    favoritedWeather.value = weatherObserved
                }

                isWeatherInitialized.value = true

                wrapperState.displayContent()
            }

        fun saveWeatherStation(sourceId: String): Job =
            launchDataLoad {
                val weatherObserved = weatherInfo.firstOrNull { it?.sourceId == sourceId }
                weatherInfo.removeIf {
                    it?.sourceId === weatherObserved?.sourceId
                }
                weatherInfo.add(0, weatherObserved)
                favoritedWeather.value = weatherObserved
                weatherRepository.saveWeatherStationId(sourceId)
            }

        fun initWeatherDetail(objectId: String?) {
            detailWrapperState.loading()
            objectId?.let {
                viewModelScope.launch {
                    selectedWeather.value = weatherRepository.getWeatherById(objectId)
                    detailWrapperState.displayContent()
                }
            }
        }
    }
