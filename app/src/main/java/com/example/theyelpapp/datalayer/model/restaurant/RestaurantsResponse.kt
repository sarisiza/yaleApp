package com.example.theyelpapp.datalayer.model.restaurant


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Restaurants Response
 */

data class RestaurantsResponse(
    @SerializedName("businesses")
    val businesses: List<Businesses>? = null,
//    @SerializedName("region")
//    val region: Region? = null,
    @SerializedName("total")
    val total: Int? = null
)