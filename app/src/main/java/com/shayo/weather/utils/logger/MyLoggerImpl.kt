package com.shayo.weather.utils.logger

import android.util.Log
import com.shayo.weather.utils.runInDebug
import javax.inject.Inject

class MyLoggerImpl @Inject constructor() : MyLogger {
    override fun logError(tag: String, log: String?) {
        Log.e("${LOGGER_TAG}/$tag", log.toString())
    }

    override fun logDebug(tag: String, log: String?) =
        runInDebug {
            Log.d("${LOGGER_TAG}/$tag", log.toString())
        }

    override fun logInfo(tag: String, log: String?) {
        Log.e("${LOGGER_TAG}/$tag", log.toString())
    }
}