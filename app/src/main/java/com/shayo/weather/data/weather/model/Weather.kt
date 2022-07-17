package com.shayo.weather.data.weather.model

import android.location.Location
import android.os.Build.VERSION_CODES.S
import androidx.room.ColumnInfo
import androidx.room.Entity

private const val IMAGE_URL_FORMAT = "https://assetambee.s3-us-west-2.amazonaws.com/weatherIcons/SVG/$S.svg"

@Entity(tableName = "Weather", primaryKeys = ["lat", "lng", "time"])
data class Weather(
    val lat: Double,
    val temperature: Double,
    val windBearing: Double,
    val windSpeed: Double,
    val apparentTemperature: Double,
    val dewPoint: Double,
    val pressure: Double,
    val windGust: Double,
    val cloudCover: Double,
    val visibility: Double,
    val ozone: Double,
    val humidity: Double,
    val uvIndex: Double,
    val time: Long,
    val summary: String,
    @ColumnInfo(name = "icon")
    private val _icon: String,
    val lng: Double,
    val precipIntensity: Double,
) {
    val icon
    get() = IMAGE_URL_FORMAT.format(_icon)

    fun isInLocation(location: Location) =
        lat == location.latitude &&
                lng == location.longitude
}