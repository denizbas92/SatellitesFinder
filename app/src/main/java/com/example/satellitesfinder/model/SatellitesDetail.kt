package com.example.satellitesfinder.model

import android.os.Parcel
import android.os.Parcelable

class SatellitesDetail() : Parcelable {
    val id: Int? = null
    val cost_per_launch: Int? = null
    val first_flight: String? = null
    val height: Int? = null
    val mass: Long? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SatellitesDetail> {
        override fun createFromParcel(parcel: Parcel): SatellitesDetail {
            return SatellitesDetail(parcel)
        }

        override fun newArray(size: Int): Array<SatellitesDetail?> {
            return arrayOfNulls(size)
        }
    }
}