package com.example.theyelpapp.api.model.restaurant


import com.google.gson.annotations.SerializedName

/**
 * Data class that defines Category
 */

data class Category(
    @SerializedName("alias")
    val alias: String? = null,
    @SerializedName("title")
    val title: String? = null
)