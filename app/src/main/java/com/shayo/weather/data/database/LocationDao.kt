package com.shayo.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shayo.weather.data.location.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getLocation(): Flow<Location?>

    @Insert
    suspend fun insertLocation(location: Location)

    @Query("DELETE FROM location")
    suspend fun nukeTable()
}