package com.shayo.weather.data.weather.network.model

sealed class TempUnits private constructor(val value: String) {
    class Si : TempUnits("si")
    class Metric : TempUnits("metric")
}