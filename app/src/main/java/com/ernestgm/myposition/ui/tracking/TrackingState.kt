package com.ernestgm.myposition.ui.tracking

sealed class TrackingState {
    object SplashData : TrackingState()
    class Error(val message: String = "") : TrackingState()
    class Loading(val loading: Boolean = true) : TrackingState()
}