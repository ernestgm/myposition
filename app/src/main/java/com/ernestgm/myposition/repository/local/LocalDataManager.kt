package com.ernestgm.myposition.repository.local

import com.ernestgm.myposition.repository.models.Device

object LocalDataManager : IRepositoryLocal {

    private lateinit var device: Device
    private var deviceList: List<Device> = emptyList()

    override fun setDevice(device: Device) {
        this.device = device
    }

    override fun getDevice() = device

    override fun setDeviceLIst(list: List<Device>) {
        this.deviceList = list
    }

    override fun getDeviceLIst() = deviceList
}
