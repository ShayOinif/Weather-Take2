package com.shayo.weather.data.weather.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    companion object {
        const val BASE_URL = "https://api.ambeedata.com/"
        const val HEADER_AUTHORIZATION = "x-api-key"
    }

    @GET("weather/latest/by-lat-lng")
    suspend fun getLatestWeather(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("units") units: String?,
    ) : WeatherApiResponse
}