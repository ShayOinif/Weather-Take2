package com.shayo.weather.data.location.model

import androidx.room.Entity

@Entity(tableName = "Location", primaryKeys = ["lat", "lng"])
data class Location(
    val lat: Double,
    val lng: Double
)