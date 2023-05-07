package com.example.satellitesfinder.model

import android.os.Parcel
import android.os.Parcelable

class SatellitesDetail() : Parcelable {
    var id: Int? = null
    var cost_per_launch: Int? = null
    var first_flight: String? = null
    var height: Int? = null
    var mass: Long? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        cost_per_launch = parcel.readInt()
        first_flight = parcel.readString()
        height = parcel.readInt()
        mass = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id!!)
        parcel.writeInt(cost_per_launch!!)
        parcel.writeString(first_flight!!)
        parcel.writeInt(height!!)
        parcel.writeLong(mass!!)
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