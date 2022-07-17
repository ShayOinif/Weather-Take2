package com.shayo.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shayo.weather.data.weather.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getWeather(): Flow<Weather?>

    @Insert
    suspend fun insertWeather(weather: Weather)

    @Query("DELETE FROM weather")
    suspend fun nukeTable()
}