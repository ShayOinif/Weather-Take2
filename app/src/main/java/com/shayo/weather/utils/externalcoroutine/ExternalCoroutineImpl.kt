package com.shayo.weather.utils.externalcoroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExternalCoroutineImpl @Inject constructor() : ExternalCoroutine {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Default
}