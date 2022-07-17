package com.shayo.weather.utils

import com.shayo.weather.BuildConfig

fun runInDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}