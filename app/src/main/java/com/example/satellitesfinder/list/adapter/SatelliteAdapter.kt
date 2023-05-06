package com.example.satellitesfinder.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.satellitesfinder.R
import com.example.satellitesfinder.databinding.SatelliteItemLayoutBinding
import com.example.satellitesfinder.model.SatelliteList
import java.lang.Boolean.TRUE
import javax.inject.Inject

class SatelliteAdapter @Inject constructor(
    private val applicationContext: Context,
    private val satelliteList: ArrayList<SatelliteList>
) :
    RecyclerView.Adapter<SatelliteAdapter.SatelliteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        val binding: SatelliteItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.satellite_item_layout, parent, false
        )
        return SatelliteViewHolder(applicationContext, binding)
    }

    override fun getItemCount(): Int {
        return satelliteList.size
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        holder.setBind(satelliteList[position])
    }

    class SatelliteViewHolder(
        private val applicationContext: Context,
        private val binding: SatelliteItemLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setBind(satellite: SatelliteList) {
            binding.tvName.text = satellite.name

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

    }
}