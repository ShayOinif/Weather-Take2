package com.shayo.weather.ui.model

import com.shayo.weather.data.weather.model.Weather

class WeatherWithAddress(
    val weather: Weather,
    val address: String?,
)