package com.shayo.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getCurrentWeather(): Flow<LocalWeather?>

    @Insert
    suspend fun insertWeather(localWeather: LocalWeather)

    @Query("DELETE FROM weather")
    suspend fun clear()
}