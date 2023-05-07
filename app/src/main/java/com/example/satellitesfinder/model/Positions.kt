package com.example.satellitesfinder.model

class Positions() {
    val list: ArrayList<SingleSatellite>?= null
}

class SingleSatellite() {
    var id: Int? = null
    var positions: ArrayList<PositionsOfSatellite>? = null
}

class PositionsOfSatellite() {
    var posX: Double? = null
    var posY: Double? = null
}