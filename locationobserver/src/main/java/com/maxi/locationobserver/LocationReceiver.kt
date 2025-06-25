package com.maxi.locationobserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager

internal class LocationReceiver(
    private val context: Context,
    private val locationCallback: LocationCallback
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                context?.let {
                    val locationState = LocationStateProvider.getLocationState(context)
                    when (locationState) {
                        is LocationState.Available -> {
                            locationCallback.onLocationEnabled()
                        }

                        is LocationState.Unavailable -> {
                            locationCallback.onLocationDisabled()
                        }
                    }
                } ?: locationCallback.onErrorReceived()
            }
        }
    }

    fun registerLocationReceiver() {
        context.registerReceiver(
            this@LocationReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )
    }

    fun unregisterLocationReceiver() {
        context.unregisterReceiver(this@LocationReceiver)
    }
}