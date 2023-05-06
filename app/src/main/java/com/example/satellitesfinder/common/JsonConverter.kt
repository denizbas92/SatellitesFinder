package com.example.satellitesfinder.common

import android.content.Context
import com.example.satellitesfinder.di.ModulesConstants
import com.example.satellitesfinder.model.Positions
import com.example.satellitesfinder.model.SatelliteList
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class JsonConverter @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    @Named(ModulesConstants.ModuleName.GSon) private val gson: Gson
) {

    fun getModelClass(fileName: String): Any {
        val inputStream = applicationContext.assets.open(fileName)
        val json = inputStream.bufferedReader().use { it.readText() }
        val mapper = ObjectMapper()

        return when (fileName) {
            JsonFileName.POSITIONS -> {
                mapper.readValue(
                    json,
                    Positions::class.java
                )
            }
            JsonFileName.SATELLITE_LIST -> {
                jsonArrayToSatelliteList(json)
            }
            else -> {
                jsonArrayToPositionList(json)
            }
        }
    }

    private fun jsonArrayToSatelliteList(jsonString: String): java.util.ArrayList<SatelliteList> {
        val type: Type = object : TypeToken<ArrayList<SatelliteList?>?>() {}.type
        return gson.fromJson(jsonString, type)
    }

    private fun jsonArrayToPositionList(jsonString: String): ArrayList<Positions> {
        val type: Type = object : TypeToken<ArrayList<Positions?>?>() {}.type
        return gson.fromJson(jsonString, type)
    }
}