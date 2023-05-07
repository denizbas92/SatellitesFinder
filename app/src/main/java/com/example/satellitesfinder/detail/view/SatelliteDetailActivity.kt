package com.example.satellitesfinder.detail.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.satellitesfinder.R
import com.example.satellitesfinder.databinding.ActivitySatelliteDetailBinding
import com.example.satellitesfinder.model.SatellitesDetail

class SatelliteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySatelliteDetailBinding

    private var satellitesDetail: SatellitesDetail? = null
    private var satelliteName = ""

    companion object {
        const val SATELLITE_DETAIL = "SATELLITE_DETAIL"
        const val SATELLITE_NAME = "SATELLITE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_satellite_detail)

        initData()

    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        satellitesDetail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(SATELLITE_DETAIL, SatellitesDetail::class.java)
        } else {
            intent.getParcelableExtra(SATELLITE_DETAIL)
        }

        satelliteName = intent.extras?.getString(SATELLITE_NAME).toString()

        if (satellitesDetail != null){
            binding.name.text = satelliteName
            binding.cost.text = satellitesDetail!!.cost_per_launch.toString()
            binding.date.text = satellitesDetail!!.first_flight.toString()
            binding.heightMass.text =
                "${satellitesDetail!!.height.toString()} / ${satellitesDetail!!.mass.toString()}"
        }
    }
}