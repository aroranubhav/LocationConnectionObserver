package com.maxi.locationobserver

import android.content.Context
import android.location.LocationManager

internal object LocationStateProvider {

    fun getLocationState(context: Context): LocationState {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isPassiveProviderEnabled = locationManager
            .isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
        val isNetworkProviderEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER)

        return if (isPassiveProviderEnabled || isNetworkProviderEnabled || isGpsEnabled) {
            LocationState.Available
        } else {
            LocationState.Unavailable
        }
    }
}
