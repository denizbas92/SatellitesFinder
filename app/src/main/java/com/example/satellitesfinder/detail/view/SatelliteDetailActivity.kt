package com.example.satellitesfinder.detail.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.satellitesfinder.R
import com.example.satellitesfinder.common.JsonConverter
import com.example.satellitesfinder.common.JsonFileName
import com.example.satellitesfinder.databinding.ActivitySatelliteDetailBinding
import com.example.satellitesfinder.model.Positions
import com.example.satellitesfinder.model.SatellitesDetail
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SatelliteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySatelliteDetailBinding

    @Inject
    lateinit var jsonConverter: JsonConverter

    private var satellitesDetail: SatellitesDetail? = null
    private var satelliteName = ""
    private var id = 0
    companion object {
        const val SATELLITE_DETAIL = "SATELLITE_DETAIL"
        const val SATELLITE_NAME = "SATELLITE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_satellite_detail)

        initData()
        intervalPosition()
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
            id = satellitesDetail?.id!!
            binding.name.text = satelliteName
            binding.cost.text = satellitesDetail!!.cost_per_launch.toString()
            binding.date.text = satellitesDetail!!.first_flight.toString()
            binding.heightMass.text =
                "${satellitesDetail!!.height.toString()} / ${satellitesDetail!!.mass.toString()}"
        }
    }

    private fun intervalPosition() {
        val positionJson = (jsonConverter.getModelClass(JsonFileName.POSITIONS) as Positions).list
        val selectedSatelliteObj = positionJson?.filter { t -> t.id == id }
        val positionList = selectedSatelliteObj?.first()?.positions
        val handler = Handler(Looper.getMainLooper())
        var counter = 0
        val runnable: Runnable = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                try {
                    val posY = positionList?.get(counter)?.posY
                    val posX = positionList?.get(counter)?.posX
                    binding.position.text = "($posY,$posX)"
                    counter++
                    if (counter == positionList?.size)
                        counter = 0
                } catch (e: Exception) {
                   e.printStackTrace()
                } finally {
                    handler.postDelayed(this, 3000)
                }
            }
        }
        handler.post(runnable)
    }
}