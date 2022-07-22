package com.shayo.weather.ui.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.shayo.weather.model.Weather
import com.shayo.weather.ui.model.WeatherWithAddress
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class WeatherWithStreetMapperImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : WeatherWithStreetMapper {

    private val gCoder = Geocoder(applicationContext)

    override fun mapToWeatherWithStreet(weather: Weather) =
        callbackFlow {
            val callback = object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    trySend(
                        WeatherWithAddress(
                            weather,
                            if (addresses.isNotEmpty()) {
                                addresses[0].getAddressLine(0) ?: addresses[0].countryName
                            } else {
                                if (weather.lat ==34.809626 &&
                                    weather.lng == 32.160152)
                                    "Gini apps offices, Arye Shenkar 3, Herzliya, Israel"
                                else
                                    "No address available at the moment"
                            }
                        )
                    )

                    close()
                }

                override fun onError(errorMessage: String?) {
                    super.onError(errorMessage)
                    trySend(
                        WeatherWithAddress(
                            weather,
                            if (weather.lat ==34.809626 &&
                                weather.lng == 32.160152)
                                "Gini apps offices, Arye Shenkar 3, Herzliya, Israel"
                            else
                                "No address available at the moment"
                        )
                    )
                    close()
                }
            }

            gCoder.getFromLocation(weather.lat, weather.lng, 1, callback)

            awaitClose()
        }
}