package com.ernestgm.myposition.ui.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ernestgm.myposition.repository.database.DBManager
import com.ernestgm.myposition.repository.models.Device
import com.ernestgm.myposition.utils.mapToDevices
import com.ernestgm.myposition.utils.toDevice
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TrackingViewModel : ViewModel() {

    private val stateLiveData = MutableLiveData<TrackingState>()
    val requestData: LiveData<TrackingState>
        get() = stateLiveData

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

    fun trackingDevices() {
        DBManager.getInstance().getAllDevice().addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
                val singleDevice = snapshot.value as Map<*, *>
                singleDevice.toDevice()?.let {
                    stateLiveData.value = TrackingState.DeviceData(it)
                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {}

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onCancelled(p0: DatabaseError) {}

        })
    }

    fun getAllDevices() {
        DBManager.getInstance().getAllDevice().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value != null) {
                    val devices = (dataSnapshot.value as Map<String?, Any?>).mapToDevices()
                    stateLiveData.value = TrackingState.DevicesListData(devices)
                } else {
                    stateLiveData.value = TrackingState.DevicesListData(emptyList())
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}