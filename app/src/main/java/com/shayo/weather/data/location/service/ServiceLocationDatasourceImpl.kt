package com.shayo.weather.data.location.service

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.shayo.weather.data.location.model.Location
import com.shayo.weather.model.WeatherAppThrowable
import com.shayo.weather.utils.gps.GpsChecker
import com.shayo.weather.utils.logger.MyLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "ServiceLocationDatasourceImpl"

class ServiceLocationDatasourceImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val gpsChecker: GpsChecker,
    private val myLogger: MyLogger,
) : ServiceLocationDatasource {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    override fun getCurrentLocation() =
        callbackFlow {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
                .addOnSuccessListener {
                    trySend(Result.success(it.convertToWeatherAppLocation()))
                    close()
                }.addOnFailureListener { e ->
                    myLogger.logError(TAG, e.message)
                    trySend(Result.failure(WeatherAppThrowable.GpsError(e.message)))
                    close()
                }

            awaitClose ()
        }
}

private fun android.location.Location.convertToWeatherAppLocation() =
    Location(
        latitude,
        longitude
    )