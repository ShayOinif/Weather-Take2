package com.shayo.weather.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailySyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    //private val weatherManager: WeatherManager,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
     /*var syncResult = Result.retry()

        weatherManager.refreshCurrentLocalWeather()
            .onSuccess {
                syncResult = Result.success()
            }

        return syncResult*/
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.shayo.weather.worker.DailySyncWorker"
    }
}