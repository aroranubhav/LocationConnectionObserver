package com.maxi.locationobserver

sealed class LocationState {

    data object Available : LocationState()

    data object Unavailable : LocationState()
}