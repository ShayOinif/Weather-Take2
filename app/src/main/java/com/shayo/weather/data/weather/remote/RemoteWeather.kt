package com.shayo.weather.data.weather.remote

import java.util.*
import java.util.stream.Stream

data class RemoteWeather(
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
) {
    fun anyFieldNull() =
        Stream.of(
            lat,
            temperature,
            windBearing,
            windSpeed,
            apparentTemperature,
            dewPoint,
            pressure,
            windGust,
            cloudCover,
            visibility,
            ozone,
            humidity,
            uvIndex,
            time,
            summary,
            icon,
            lng,
            precipIntensity,
        ).anyMatch(Objects::isNull)
}