package com.maxi.locationobserver

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationObserver(
    private val context: Context
) {

    private lateinit var locationReceiver: LocationReceiver

    private val _locationState = MutableStateFlow<LocationState>(LocationState.Unavailable)
    val locationState = _locationState.asStateFlow()

    private val locationCallback = object : LocationCallback {
        override fun onLocationEnabled() {
            _locationState.value = LocationState.Available
        }

        override fun onLocationDisabled() {
            _locationState.value = LocationState.Unavailable
        }

        override fun onErrorReceived() {
            Log.d(TAG, "onErrorReceived: ")
            _locationState.value = LocationState.Unavailable
        }
    }

    init {
        init(context)
    }

    private fun init(context: Context) {
        _locationState.value = LocationStateProvider.getLocationState(context)
        registerLocationReceiver()
    }

    private fun registerLocationReceiver() {
        if (!::locationReceiver.isInitialized) {
            locationReceiver = LocationReceiver(context, locationCallback)
            locationReceiver.registerLocationReceiver()
        }
    }

    fun unregisterLocationReceiver() {
        if (::locationReceiver.isInitialized) {
            locationReceiver.unregisterLocationReceiver()
        }
    }
}

const val TAG = "LocationObserverTAG"