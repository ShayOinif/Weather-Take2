package com.shayo.weather.data.weather.network.model

import com.shayo.weather.data.weather.model.Weather

private const val MESSAGE_SUCCESS = "success"

data class WeatherApiResponse(
    val message: String,
    val data: Weather?
) {
    fun isSuccess() =
        message == MESSAGE_SUCCESS
}