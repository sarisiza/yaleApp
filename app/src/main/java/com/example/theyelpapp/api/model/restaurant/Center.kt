package com.example.theyelpapp.api.model.restaurant


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Center
 */

data class Center(
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null
)