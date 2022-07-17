package com.shayo.weather.data.location.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.shayo.weather.data.location.model.Location
import com.shayo.weather.model.WeatherAppThrowable
import com.shayo.weather.utils.gps.GpsChecker
import com.shayo.weather.utils.logger.MyLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "LocationDatasourceImpl"

class ServiceLocationDatasourceImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val gpsChecker: GpsChecker,
    private val myLogger: MyLogger,
) : ServiceLocationDatasource {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    override fun getCurrentLocation() =
        callbackFlow {
            if (!gpsChecker.hasGps.replayCache.last()) {
                myLogger.logError(TAG, "No gps at the moment")
                trySend(Result.failure(WeatherAppThrowable.GpsError("No gps at the moment")))
                close()
            }

            if (!appContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                !appContext.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
                myLogger.logError(TAG, "Tried to get location but no permission")
                trySend(Result.failure(WeatherAppThrowable.MissingPermission()))
                close()
            }

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
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

private fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
        return true
    }

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

private fun android.location.Location.convertToWeatherAppLocation() =
    Location(
        latitude,
        longitude
    )