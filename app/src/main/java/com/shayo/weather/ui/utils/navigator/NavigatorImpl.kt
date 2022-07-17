package edu.shayo.whatsapp.ui.navigator

import com.shayo.weather.di.HomeFragmentNavigator
import com.shayo.weather.ui.utils.navigator.Navigator
import com.shayo.weather.ui.utils.navigator.NavigatorParams
import com.shayo.weather.ui.home.HomeFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    @HomeFragmentNavigator
    private val loginFragmentNavigator: Navigator
) : Navigator {
    override fun resolveNavigation(navigationParams: NavigatorParams) {
        when (navigationParams.fragment) {
            is HomeFragment -> loginFragmentNavigator.resolveNavigation(navigationParams)
        }
    }
}