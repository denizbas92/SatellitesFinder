package com.example.satellitesfinder.common

import android.content.Context
import com.example.satellitesfinder.model.Positions
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonConverter @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    fun getModelClass(fileName: String): Any {
        val inputStream = applicationContext.assets.open(fileName)
        val json = inputStream.bufferedReader().use { it.readText() }
        val mapper = ObjectMapper()
        var model: Any? = null

        model = when (fileName) {
            JsonFileName.POSITIONS -> {
                mapper.readValue(
                    json,
                    Positions::class.java
                )
            }
            else -> {
                JSONArray(json)
            }
        }

        return model!!
    }
}