package com.ernestgm.myposition.repository.local

import com.ernestgm.myposition.repository.models.Device

object LocalDataManager : IRepositoryLocal {

    private lateinit var device: Device

    override fun setDevice(device: Device) {
        this.device = device
    }

    override fun getDevice(): Device {
        return device
    }

}
