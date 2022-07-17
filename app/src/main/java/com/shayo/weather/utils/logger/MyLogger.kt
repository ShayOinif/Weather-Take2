package com.shayo.weather.utils.logger

const val LOGGER_TAG = "WeatherApp"

interface MyLogger {
    fun logError(tag: String, log: String?)

    fun logDebug(tag: String, log: String?)

    fun logInfo(tag: String, log: String?)
}