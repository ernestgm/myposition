package com.ernestgm.myposition.repository.models

class Device(
    val id: String,
    val name: String,
    var latitude: Double,
    var longitude: Double
) {
    fun updateLocation(lat: Double, long: Double) {
        this.latitude = lat
        this.longitude = long

    }
    fun toMap(): MutableMap<String, Any> {
        val map = HashMap<String, Any>()

        map["name"] = this.name
        map["latitude"] = this.latitude
        map["longitude"] = this.longitude

        return map
    }
}