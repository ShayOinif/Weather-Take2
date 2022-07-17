package com.shayo.weather.ui.utils.navigator

import android.os.Parcelable
import androidx.fragment.app.Fragment

interface Navigator {
    fun resolveNavigation(navigationParams: NavigatorParams)
}

data class NavigatorParams(
    val fragment: Fragment,
    val navigatorParams: List<Parcelable>? = null,
)