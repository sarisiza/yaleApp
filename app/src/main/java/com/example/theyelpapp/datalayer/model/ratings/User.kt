package com.example.theyelpapp.datalayer.model.ratings


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Users
 */

data class User(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("profile_url")
    val profileUrl: String? = null
)