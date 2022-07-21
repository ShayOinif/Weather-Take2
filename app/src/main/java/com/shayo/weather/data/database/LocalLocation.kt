package com.shayo.weather.data.database

import androidx.room.Entity

@Entity(tableName = "Location", primaryKeys = ["lat", "lng", "time"])
data class LocalLocation(
    val lat: Double,
    val lng: Double,
    val time: Long
)