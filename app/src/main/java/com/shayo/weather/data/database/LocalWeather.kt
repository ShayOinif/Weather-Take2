package com.shayo.weather.data.database

import androidx.room.Entity

@Entity(tableName = "Weather", primaryKeys = ["lat", "lng", "time"])
data class LocalWeather(
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
    val icon: String,
    val lng: Double,
    val precipIntensity: Double,
)