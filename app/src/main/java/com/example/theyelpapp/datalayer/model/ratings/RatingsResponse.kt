package com.example.theyelpapp.datalayer.model.ratings


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Ratings Response
 */

data class RatingsResponse(
    @SerializedName("possible_languages")
    val possibleLanguages: List<String?>? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("total")
    val total: Int? = null
)