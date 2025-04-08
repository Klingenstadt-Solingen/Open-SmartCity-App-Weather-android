package de.osca.android.weather.data.remote

import de.osca.android.essentials.utils.annotations.UnwrappedResponse
import de.osca.android.weather.domain.entity.WeatherObserved
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiService {
    @GET("classes/WeatherObserved")
    suspend fun getWeatherObserved(): Response<List<WeatherObserved>>

    @GET("classes/WeatherObserved/{objectId}")
    @UnwrappedResponse
    suspend fun getWeatherById(
        @Path("objectId") objectId: String,
    ): Response<WeatherObserved>
}
