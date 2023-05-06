package com.example.satellitesfinder.common

import com.example.satellitesfinder.model.SatellitesDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheMechanism @Inject constructor(){

    private var setOfSatelliteCache  = mutableSetOf<SatellitesDetail>()
    private lateinit var satellite: ArrayList<SatellitesDetail>

    fun addToCache(satellite: SatellitesDetail) {
        setOfSatelliteCache.add(satellite)
    }

    fun checkToCache(id: Int?): Boolean {
        val isExist = setOfSatelliteCache.filter { t -> t.id == id }
        this.satellite = isExist as ArrayList<SatellitesDetail>
        if (isExist.isEmpty())
            return false
        return true
    }

    fun getSatellite(): ArrayList<SatellitesDetail> {
        return this.satellite
    }
}