package com.ernestgm.myposition.repository.local

import com.ernestgm.myposition.repository.models.Device

interface IRepositoryLocal {
    fun setDevice(device: Device)
    fun getDevice(): Device
}
