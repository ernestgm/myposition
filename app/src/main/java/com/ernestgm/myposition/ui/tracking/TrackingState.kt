package com.ernestgm.myposition.ui.tracking

import com.ernestgm.myposition.repository.models.Device

sealed class TrackingState {
    object SplashData : TrackingState()
    class DevicesListData(val devices: List<Device>) : TrackingState()
    class DeviceData(val device: Device) : TrackingState()
    class Error(val message: String = "") : TrackingState()
    class Loading(val loading: Boolean = true) : TrackingState()
}