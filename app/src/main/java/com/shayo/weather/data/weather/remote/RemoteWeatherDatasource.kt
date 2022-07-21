package com.shayo.weather.data.weather.remote

interface RemoteWeatherDatasource {
    suspend fun getWeatherByCoordinates(
        lat: Double,
        lng: Double,
        tempUnits: TempUnits = TempUnits.Si
    ): Result<RemoteWeather>
}