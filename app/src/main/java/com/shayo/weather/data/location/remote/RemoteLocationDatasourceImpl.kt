package com.shayo.weather.data.location.remote

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.shayo.weather.utils.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "ServiceLocationDatasourceImpl"
private const val NO_LOCATION_MESSAGE = "Location is null"

class RemoteLocationDatasourceImpl @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val myLogger: MyLogger,
) : RemoteLocationDatasource {

    override fun getCurrentLocation() =
        callbackFlow {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { locationResult ->
                locationResult?.let { location ->
                    myLogger.logInfo(TAG, location.toString())
                    trySend(Result.success(location))
                    close()
                } ?: let {
                    myLogger.logInfo(TAG, NO_LOCATION_MESSAGE)
                    trySend(Result.failure(WeatherAppThrowable.NoRemoteLocation))
                    close()
                }
            }.addOnFailureListener { cause ->
                myLogger.logError(TAG, cause.message)
                trySend(Result.failure(WeatherAppThrowable.GpsError(cause.message)))
                close()
            }

            awaitClose()
        }


            /*Result.failure<Location>(WeatherAppThrowable.NoRemoteLocation)

        fusedLocationClient.getCurrentLocation(
            PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { locationResult ->
                locationResult?.let { location ->
                    result = Result.success(location)
                } ?: myLogger.logInfo(TAG, NO_LOCATION_MESSAGE)
            }.addOnFailureListener { cause ->
                myLogger.logError(TAG, cause.message)
                result = Result.failure(WeatherAppThrowable.GpsError(cause.message))
            }

        return result
    }*/
}