package com.example.theyelpapp.datalayer.domain

import com.example.theyelpapp.datalayer.model.ratings.Review

/**
 * Data class that defines a rating
 */
data class Rating(
    val id: String = "",
    val rating: Int = 0,
    val text: String = "",
    val timeCreated: String = "",
    val userImg: String?,
    val userName: String = ""
)

fun List<Review>?.mapToRating(): List<Rating> =
    this?.map {
        Rating(
            it.id ?: "",
            it.rating ?: 0,
            it.text ?: "",
            it.timeCreated ?: "",
            it.user?.imageUrl,
            it.user?.name ?: ""
        )
    }?: emptyList()
