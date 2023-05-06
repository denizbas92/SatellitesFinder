package com.example.satellitesfinder.list.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

    @Inject
    lateinit var satelliteAdapter: SatelliteAdapter


    private lateinit var binding: ActivityListSatelliteBinding
    private var listOfSatellite: ArrayList<SatelliteList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_satellite)

        initAdapter()
        searchList()
    }

    private fun initAdapter() {
        listOfSatellite = getSatelliteList()
        if (listOfSatellite.isNullOrEmpty())
            return

        satelliteAdapter.setFilteredList(listOfSatellite)
        binding.recSatellite.layoutManager = LinearLayoutManager(this)
        binding.recSatellite.addItemDecoration(setDivider())
        binding.recSatellite.adapter = satelliteAdapter
    }

    private fun searchList() {
        binding.search.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                listOfSatellite = if (newText.isNullOrEmpty()) {
                    getSatelliteList()
                } else {
                    val tempList = listOfSatellite?.filter { t ->
                        t.name?.lowercase()?.contains(
                            newText.lowercase()
                        )!!
                    }
                    tempList as ArrayList<SatelliteList>?
                }
                satelliteAdapter.setFilteredList(listOfSatellite)
                return false
            }
        })
    }

    private fun visibilityOfLottie(isVisible: Boolean) {
        if (isVisible) {
            binding.recSatellite.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.recSatellite.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
        }
    }

    private fun getSatelliteList(): ArrayList<SatelliteList> {
        visibilityOfLottie(isVisible = true)
        Handler(Looper.getMainLooper()).postDelayed({
            visibilityOfLottie(isVisible = false)
        }, 2000)
        return jsonConverter.getModelClass(JsonFileName.SATELLITE_LIST) as ArrayList<SatelliteList>
    }

    private fun setDivider(): DividerItemDecoration {
        val verticalDecoration = DividerItemDecoration(
            binding.recSatellite.context,
            DividerItemDecoration.VERTICAL
        )
        val verticalDivider = ContextCompat.getDrawable(this, R.drawable.rec_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        return verticalDecoration
    }
}