package com.example.satellitesfinder.list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.satellitesfinder.R
import com.example.satellitesfinder.common.CacheMechanism
import com.example.satellitesfinder.common.JsonConverter
import com.example.satellitesfinder.common.JsonFileName
import com.example.satellitesfinder.databinding.SatelliteItemLayoutBinding
import com.example.satellitesfinder.detail.view.SatelliteDetailActivity
import com.example.satellitesfinder.detail.view.SatelliteDetailActivity.Companion.SATELLITE_DETAIL
import com.example.satellitesfinder.detail.view.SatelliteDetailActivity.Companion.SATELLITE_NAME
import com.example.satellitesfinder.model.SatelliteList
import com.example.satellitesfinder.model.SatellitesDetail
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Boolean.TRUE
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
class SatelliteAdapter @Inject constructor(
    private val cacheMechanism: CacheMechanism,
    private val jsonConverter: JsonConverter,
    @ApplicationContext private val applicationContext: Context
) : RecyclerView.Adapter<SatelliteAdapter.SatelliteViewHolder>() {

    private var satelliteList: ArrayList<SatelliteList>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        val binding: SatelliteItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.satellite_item_layout, parent, false
        )
        return SatelliteViewHolder(binding, cacheMechanism, applicationContext, jsonConverter)
    }

    override fun getItemCount(): Int {
        return satelliteList?.size!!
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        holder.setBind(satelliteList?.get(position)!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(listOfSatellite: java.util.ArrayList<SatelliteList>?) {
        if (listOfSatellite != null) {
            this.satelliteList = listOfSatellite
        }
        notifyDataSetChanged()
    }

    @Singleton
    class SatelliteViewHolder(
        private val binding: SatelliteItemLayoutBinding,
        private val cacheMechanism: CacheMechanism,
        private val applicationContext: Context,
        private val jsonConverter: JsonConverter
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setBind(satellite: SatelliteList) {

            binding.tvName.text = satellite.name
            binding.baseLayout.setOnClickListener {
                if (cacheMechanism.checkToCache(satellite.id)) {
                    val satellitesDetail = cacheMechanism.getSatellite().first()
                    goToDetailActivity(satellitesDetail,satellite.name)
                } else {
                    val detailOfSatellite: ArrayList<SatellitesDetail> =
                        jsonConverter.getModelClass(
                            JsonFileName.SATELLITE_DETAIL
                        ) as ArrayList<SatellitesDetail>
                    val detail = detailOfSatellite.filter { t -> t.id == satellite.id }
                    cacheMechanism.addToCache(detail.first())
                    goToDetailActivity(detail.first(), satellite.name)
                }
            }

            when (satellite.active) {
                TRUE -> {
                    binding.tvState.text = applicationContext.getString(R.string.active)
                    binding.imgState.background = ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.circle_yellow
                    )
                }
                else -> {
                    binding.tvState.text = applicationContext.getString(R.string.passive)
                    binding.imgState.background = ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.circle_red
                    )
                }
            }
        }

        private fun goToDetailActivity(detail: SatellitesDetail, name: String?) {
            val intent = Intent(applicationContext, SatelliteDetailActivity::class.java)
            intent.putExtra(SATELLITE_DETAIL, detail)
            intent.putExtra(SATELLITE_NAME,name)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            applicationContext.startActivity(intent)
        }

    }
}