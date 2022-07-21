package com.shayo.weather.utils

import retrofit2.HttpException
import java.io.IOException

sealed class WeatherAppThrowable(message: String? = null) : Throwable(message) {
    class NoInternet(message: String? = null) : WeatherAppThrowable(message)
    class HttpError(message: String?= null) : WeatherAppThrowable(message)
    class InternalError(message: String?= null) : WeatherAppThrowable(message)
    class UnexpectedError(message: String? = null) : WeatherAppThrowable(message)
    class MissingPermission(): WeatherAppThrowable()
    class GpsError(message: String? = null): WeatherAppThrowable(message)
    object NoRemoteLocation : WeatherAppThrowable()
    object NoCurrentWeather : WeatherAppThrowable()
}

fun Throwable.mapToWeatherAppThrowable() =
    when (this) {
        is IOException -> WeatherAppThrowable.NoInternet(message)
        is HttpException -> WeatherAppThrowable.HttpError(message)
        else -> WeatherAppThrowable.UnexpectedError(message)
    }