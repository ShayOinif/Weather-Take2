package com.shayo.weather.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val REFRESH_INTERVAL = 10_000L

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    //private val weatherManager: WeatherManager,
   // private val weatherWithStreetMapper: WeatherWithStreetMapper,
) : ViewModel() {

    val _status = MutableStateFlow<HomeFragmentState>(HomeFragmentState.CheckingPermissions)

   /* fun getLocalWeather() =
        weatherManager.getLatestLocalWeather(REFRESH_INTERVAL).map { weatherResult ->

            weatherResult.onSuccess { weather ->
                weatherWithStreetMapper.mapToWeatherWithStreet(weather)
            }
        }*/

    fun checkPermissions(
        homeFragment: HomeFragment,
    ) {

        viewModelScope.launch(Dispatchers.Default) {
            _status.emit(HomeFragmentState.CheckingPermissions)

           /* permissionManager.checkPermissions(
                PermissionRequest(
                    homeFragment,
                    listOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    R.string.fine_location_permission,
                    R.string.fine_location_rationale,
                    R.string.fine_location_no_permission_rationale,
                ) {
                    viewModelScope.launch {
                        _status.emit(
                            if (it.containsValue(false)) {
                                HomeFragmentState.NoPermissions
                            } else {
                                HomeFragmentState.OK
                            }
                        )
                    }
                }
            )*/
        }
    }
}

sealed class HomeFragmentState {
    object NoPermissions : HomeFragmentState()

    class Error(
        @StringRes
        val errorMessageId: Int,
    ) : HomeFragmentState()

    object CheckingPermissions : HomeFragmentState()

    object OK : HomeFragmentState()
}