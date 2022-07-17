package com.shayo.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shayo.weather.data.location.model.Location
import com.shayo.weather.data.weather.model.Weather

@Database(entities = [Weather::class, Location::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DB_NAME = "WeatherDatabase.db"
    }
}