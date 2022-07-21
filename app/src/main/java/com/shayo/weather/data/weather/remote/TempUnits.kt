package com.shayo.weather.data.weather.remote

sealed class TempUnits private constructor(val value: String) {
    object Si : TempUnits("si")
    object Metric : TempUnits("metric")
}