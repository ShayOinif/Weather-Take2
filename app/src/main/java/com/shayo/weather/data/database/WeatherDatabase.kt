package com.shayo.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalWeather::class, LocalLocation::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    abstract fun locationDao(): LocationDao

    companion object {
        const val DB_NAME = "WeatherDatabase.db"
    }
}