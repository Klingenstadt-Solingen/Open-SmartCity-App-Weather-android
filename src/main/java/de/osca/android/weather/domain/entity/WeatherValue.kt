package de.osca.android.weather.domain.entity

import com.google.gson.annotations.SerializedName

data class WeatherValue(
    @SerializedName("unit")
    val unit: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("value")
    val value: Float? = null,
    @SerializedName("iconUrl")
    val iconUrl: String? = null
)