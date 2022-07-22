package com.shayo.weather.data.location.remote

import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity.GRANULARITY_PERMISSION_LEVEL
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
            val request = CurrentLocationRequest.Builder()
                .setGranularity(GRANULARITY_PERMISSION_LEVEL)
                .setMaxUpdateAgeMillis(0)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()
            myLogger.logInfo(TAG, "Collecting remote location")
            fusedLocationClient.getCurrentLocation(
                request,
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

            awaitClose {
                myLogger.logInfo(TAG, "Closed remote location flow")
            }
        }
}