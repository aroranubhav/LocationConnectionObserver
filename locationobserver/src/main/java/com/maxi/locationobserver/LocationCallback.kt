package com.maxi.locationobserver

internal interface LocationCallback {

    fun onLocationEnabled()

    fun onLocationDisabled()

    fun onErrorReceived()
}