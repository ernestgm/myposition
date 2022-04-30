package com.ernestgm.myposition.ui.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ernestgm.myposition.repository.database.DBManager
import com.ernestgm.myposition.repository.models.Device

class TrackingViewModel : ViewModel() {

    private val stateLiveData = MutableLiveData<TrackingState>()
    val requestData: LiveData<TrackingState>
        get() = stateLiveData

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun addDevice(device: Device) {
        stateLiveData.value = TrackingState.Loading()
        DBManager.getInstance().addDevice(device).addOnCompleteListener {
            stateLiveData.value = TrackingState.Loading(false)
            stateLiveData.value = TrackingState.SplashData
        }.addOnFailureListener {
            stateLiveData.value = TrackingState.Loading(false)
            stateLiveData.value = it.message?.let { msg -> TrackingState.Error(msg) }
        }
    }

    fun removeDevice(device: Device) {
        stateLiveData.value = TrackingState.Loading()
        DBManager.getInstance().removeDevice(device).addOnCompleteListener {
            stateLiveData.value = TrackingState.Loading(false)
            stateLiveData.value = TrackingState.SplashData
        }.addOnFailureListener {
            stateLiveData.value = TrackingState.Loading(false)
            stateLiveData.value = it.message?.let { msg -> TrackingState.Error(msg) }
        }
    }

    fun updateDevice(device: Device) {
        DBManager.getInstance().updateDevice(device)
    }
}