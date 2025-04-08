package de.osca.android.weather.domain.entity

import com.google.gson.annotations.SerializedName
import de.osca.android.essentials.domain.entity.Coordinates
import de.osca.android.essentials.domain.entity.DateEnvelope
import de.osca.android.essentials.domain.entity.ParseDate
import de.osca.android.essentials.utils.extensions.toLocalDateTime

data class WeatherObserved (
    @SerializedName("objectId")
    val objectId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("shortName")
    val shortName: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("district")
    val district: String? = null,
    @SerializedName("sourceId")
    val sourceId: String = "",
    @SerializedName("geopoint")
    val location: Coordinates? = Coordinates(),
    @SerializedName("values")
    val values: WeatherValues = WeatherValues(),
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("dateObserved")
    val dateObserved: ParseDate? = null
){
    val lastObservedDate get() = dateObserved?.iso?.toLocalDateTime()
}