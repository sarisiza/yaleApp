package com.example.theyelpapp.data.model.restaurant


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Coordinates
 */

data class Coordinates(
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null
)