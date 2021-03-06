package com.shayo.weather.di

import android.content.Context
import androidx.room.Room
import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.database.LocationDao
import com.shayo.weather.data.database.WeatherDao
import com.shayo.weather.data.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }

    @Provides
    fun provideLocationDao(weatherDatabase: WeatherDatabase): LocationDao {
        return weatherDatabase.locationDao()
    }

    private fun getDefaultLocation() =
    LocalLocation(
        DEFAULT_LAT,
        DEFAULT_LNG,
        System.currentTimeMillis()
    )

private const val DEFAULT_LAT = 34.809626
private const val DEFAULT_LNG = 32.160152

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext appContext: Context,
    ) =
        Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            WeatherDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
}