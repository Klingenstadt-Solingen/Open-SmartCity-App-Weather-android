package de.osca.android.weather.domain.entity

import com.google.gson.annotations.SerializedName
import de.osca.android.essentials.domain.entity.CardinalDirection

data class WeatherValues(
    @SerializedName("lufttemperatur")
    val temperature: WeatherValue? = WeatherValue(),
    @SerializedName("relative_luftfeuchte")
    val relativeHumidity: WeatherValue? = WeatherValue(),
    @SerializedName("realtiver_luftdruck")
    val relativePressure: WeatherValue? = WeatherValue(),
    @SerializedName("windgeschwindigkeit_kmh")
    val windSpeed: WeatherValue? = WeatherValue(),
    @SerializedName("windrichtung")
    val windDirection: WeatherValue? = WeatherValue(),
    @SerializedName("niederschlagsintensitaet")
    val rainIntensity: WeatherValue? = WeatherValue(),
    @SerializedName("uv_index")
    val uvIndex: WeatherValue? = WeatherValue(),
    @SerializedName("lufttemperatur_avg")
    val temperatureAverage: WeatherValue? = WeatherValue(),
    @SerializedName("relative_luftfeuchte_avg")
    val airHumidityAverage: WeatherValue? = WeatherValue(),
    @SerializedName("realtiver_luftdruck_avg")
    val relativePressureAverage: WeatherValue? = WeatherValue(),
    @SerializedName("windgeschwindigkeit_kmh_avg")
    val windSpeedAverage: WeatherValue? = WeatherValue(),
    @SerializedName("windrichtung_avg")
    val windDirectionAverage: WeatherValue? = WeatherValue(),
    @SerializedName("niederschlagsintensitaet_avg")
    val rainIntensityAverage: WeatherValue? = WeatherValue(),
    @SerializedName("uv_index_avg")
    val uvIndexAverage: WeatherValue? = WeatherValue(),
    @SerializedName("sonnenaufgang")
    val sunrise: WeatherValue? = WeatherValue(),
    @SerializedName("sonnenuntergang")
    val sunset: WeatherValue? = WeatherValue(),
    @SerializedName("badeampel")
    val badeampel: WeatherValue? = WeatherValue(),
    @SerializedName("wasserspiel")
    val wasserspiel: WeatherValue? = WeatherValue()
) {
    val windCardinalDirection get() = CardinalDirection.fromDegree(windDirection?.value ?: 0f)

    val isValid get() = rainIntensity?.unit != null
}