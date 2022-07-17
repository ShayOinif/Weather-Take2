package com.shayo.weather.ui.utils.navigator

import androidx.navigation.fragment.findNavController
import com.shayo.weather.ui.home.HomeFragmentDirections
import javax.inject.Inject

class HomeFragmentNavigatorImpl @Inject constructor() : Navigator {
    override fun resolveNavigation(navigationParams: NavigatorParams) {
        navigationParams.fragment.findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMapFragment()
        )
    }
}