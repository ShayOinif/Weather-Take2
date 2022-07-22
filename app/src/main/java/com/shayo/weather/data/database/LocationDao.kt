package com.shayo.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getCurrentLocation(): Flow<LocalLocation>

    @Insert
    suspend fun insertLocation(localLocation: LocalLocation)

    @Query("DELETE FROM location")
    suspend fun clear()
}