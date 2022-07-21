package com.shayo.weather.data.weather.remote

private const val MESSAGE_SUCCESS = "success"

data class WeatherApiResponse(
    val message: String,
    val data: RemoteWeather?
) {
    fun isSuccess() =
        message == MESSAGE_SUCCESS
}