package com.shayo.weather.data.utils

import com.shayo.weather.utils.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger

suspend fun performCatching(
    myLogger: MyLogger,
    tag: String,
    block: suspend () -> Unit,
) =
    try {
        block()
        Result.success(null)
    } catch (t: Throwable) {
        myLogger.logError(tag, t.message)
        Result.failure(WeatherAppThrowable.InternalError(t.message))
    }