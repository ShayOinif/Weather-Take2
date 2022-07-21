package com.shayo.weather.model

private const val IMAGE_URL_FORMAT = "https://assetambee.s3-us-west-2.amazonaws.com/weatherIcons/SVG/%s.svg"

data class Weather(
    val lat: Double,
    val lng: Double,
    val temperature: Double,
    val summary: String,
    private val _icon: String,
) {
    val icon
        get() = IMAGE_URL_FORMAT.format(_icon)
}