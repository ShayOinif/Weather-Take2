package com.shayo.weather.data.weather.remote

import com.shayo.weather.utils.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import com.shayo.weather.utils.mapToWeatherAppThrowable
import javax.inject.Inject

private const val EMPTY_RESPONSE_MESSAGE = "Empty response somehow"
private const val EMPTY_FIELDS = "Empty fields somehow"
private const val TAG = "RemoteWeatherDatasourceImpl"

class RemoteWeatherDatasourceImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val myLogger: MyLogger,
) : RemoteWeatherDatasource {

    override suspend fun getWeatherByCoordinates(
        lat: Double,
        lng: Double,
        tempUnits: TempUnits
    ): Result<RemoteWeather> =
        kotlin.runCatching {
            weatherApi.getLatestWeather(lat, lng, tempUnits.value)
        }.fold (
            onSuccess = { weatherApiResponse ->
                if (weatherApiResponse.isSuccess()) {
                    weatherApiResponse.data?.let { remoteWeather ->
                        if (remoteWeather.anyFieldNull()) {
                            myLogger.logError(TAG, EMPTY_FIELDS)
                            Result.failure(WeatherAppThrowable.HttpError(EMPTY_FIELDS))
                        } else {
                            Result.success(remoteWeather)
                        }
                    } ?: let {
                        myLogger.logError(TAG, EMPTY_RESPONSE_MESSAGE)

                        Result.failure(
                            WeatherAppThrowable.UnexpectedError(
                                EMPTY_RESPONSE_MESSAGE
                            )
                        )
                    }
                } else {
                    myLogger.logError(TAG, weatherApiResponse.message)

                    Result.failure(
                        WeatherAppThrowable.HttpError(weatherApiResponse.message)
                    )
                }
            },
            onFailure = { cause ->
                myLogger.logError(TAG, cause.message)

                Result.failure(cause.mapToWeatherAppThrowable())
            }
        )
}