package com.ernestgm.myposition.repository.database

import com.ernestgm.myposition.repository.models.Device
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class DBManager {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference(Device::class.simpleName!!)

    fun getAllDevice(): Query {
        return databaseReference.orderByKey()
    }

    fun addDevice(device: Device): Task<Void> {
        return databaseReference.child(device.id).setValue(device)
    }

    fun updateDevice(device: Device): Task<Void> {
        return databaseReference.child(device.id).updateChildren(device.toMap())
    }

    fun removeDevice(device: Device) : Task<Void> {
        return databaseReference.child(device.id).removeValue()
    }


    companion object {
        private lateinit var instance: DBManager

        fun getInstance(): DBManager {
            if (!Companion::instance.isInitialized) {
                instance = DBManager()
            }

            return instance
        }
    }
}