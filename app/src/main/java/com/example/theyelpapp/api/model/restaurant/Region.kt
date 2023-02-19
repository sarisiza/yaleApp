package com.example.theyelpapp.api.model.restaurant


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Region
 */

data class Region(
    @SerializedName("center")
    val center: Center? = null
)