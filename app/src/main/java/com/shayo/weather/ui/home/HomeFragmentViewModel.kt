package com.shayo.weather.ui.home

import androidx.lifecycle.ViewModel
import com.shayo.weather.domain.GetLocalWeatherUseCase
import com.shayo.weather.ui.model.WeatherWithAddress
import com.shayo.weather.ui.utils.WeatherWithStreetMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val REFRESH_INTERVAL = 10_000L

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getLocalWeatherUseCase: GetLocalWeatherUseCase,
    private val weatherWithStreetMapper: WeatherWithStreetMapper
) : ViewModel() {

    val localWeatherFlow = getLocalWeatherUseCase.invoke().filter { it.isSuccess }.map { weatherResult ->
        weatherResult.getOrNull().let {
            it?.let { weather ->
                LocalWeather.NotEmpty(
                    weatherWithStreetMapper.mapToWeatherWithStreet(weather)
                )
            } ?: LocalWeather.Empty
        }
    }

   /* fun getLocalWeather() =
        weatherManager.getLatestLocalWeather(REFRESH_INTERVAL).map { weatherResult ->

            weatherResult.onSuccess { weather ->
                weatherWithStreetMapper.mapToWeatherWithStreet(weather)
            }
        }*/
}

sealed class LocalWeather {
    class NotEmpty(val weatherWithAddress: WeatherWithAddress) : LocalWeather()
    object Empty : LocalWeather()
}