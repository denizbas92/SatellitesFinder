package com.example.satellitesfinder.list.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satellitesfinder.R
import com.example.satellitesfinder.common.JsonConverter
import com.example.satellitesfinder.common.JsonFileName
import com.example.satellitesfinder.databinding.ActivityListSatelliteBinding
import com.example.satellitesfinder.list.adapter.SatelliteAdapter
import com.example.satellitesfinder.model.SatelliteList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class SatelliteListActivity : AppCompatActivity() {

    @Inject
    lateinit var jsonConverter: JsonConverter

    private lateinit var satelliteAdapter: SatelliteAdapter
    private lateinit var binding: ActivityListSatelliteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_list_satellite)

        initAdapter()

    }

    private fun initAdapter() {
        satelliteAdapter = SatelliteAdapter(this,getSatelliteList())
        binding.recSatellite.layoutManager = LinearLayoutManager(this)
        binding.recSatellite.addItemDecoration(setDivider())
        binding.recSatellite.adapter = satelliteAdapter
    }

    private fun getSatelliteList() : ArrayList<SatelliteList> {
        return  jsonConverter.getModelClass(JsonFileName.SATELLITE_LIST) as ArrayList<SatelliteList>
    }

    private fun setDivider(): DividerItemDecoration{
        val verticalDecoration = DividerItemDecoration(
            binding.recSatellite.context,
            DividerItemDecoration.VERTICAL
        )
        val verticalDivider = ContextCompat.getDrawable(this,R.drawable.rec_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        return verticalDecoration
    }
}