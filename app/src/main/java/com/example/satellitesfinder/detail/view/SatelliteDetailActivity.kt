package com.example.satellitesfinder.detail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.satellitesfinder.R

class SatelliteDetailActivity : AppCompatActivity() {

    companion object {
        const val SATELLITE_DETAIL = "SATELLITE_DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satellite_detail)
    }
}