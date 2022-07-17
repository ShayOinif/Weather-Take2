package com.shayo.weather.data.weather.network

import com.shayo.weather.data.weather.network.model.TempUnits
import com.shayo.weather.model.WeatherAppThrowable
import com.shayo.weather.model.mapToWeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import com.shayo.weather.utils.networkstatuschecker.NetworkStatusChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val NO_INTERNET_MESSAGE = "Tried to initiate a call but no internet"
private const val EMPTY_RESPONSE_MESSAGE = "Empty response somehow"
private const val TAG = "RemoteWeatherDatasourceImpl"

class RemoteWeatherDatasourceImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val networkStatusChecker: NetworkStatusChecker,
    private val myLogger: MyLogger,
) : RemoteWeatherDatasource {

    override fun getLatestWeather(
        lat: Double,
        lng: Double,
        tempUnits: TempUnits
    ) =
        flow {
            emit(
                if (networkStatusChecker.hasInternet.replayCache.last()) {

                    kotlin.runCatching {
                        weatherApi.getLatestWeather(lat, lng, tempUnits.value)
                    }.fold(
                        { weatherApiResponse ->
                            if (weatherApiResponse.isSuccess()) {
                                weatherApiResponse.data?.let {
                                    Result.success(it)
                                } ?: Result.failure(
                                    WeatherAppThrowable.UnexpectedError(
                                        EMPTY_RESPONSE_MESSAGE
                                    )
                                )
                            } else {
                                Result.failure(
                                    WeatherAppThrowable.HttpError(weatherApiResponse.message)
                                )
                            }
                        },
                        {
                            Result.failure(it.mapToWeatherAppThrowable())
                        }
                    )
                } else {
                    NO_INTERNET_MESSAGE.let {
                        myLogger.logInfo(TAG, it)

                        Result.failure(WeatherAppThrowable.NoInternet(NO_INTERNET_MESSAGE))
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
}