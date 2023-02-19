package com.example.theyelpapp.api.domain

import com.example.theyelpapp.api.model.restaurant.Businesses


/**
 * Data class that defines a restaurant
 */

data class Restaurant(
    val id: String = "",
    val categoryTitle: List<String> = listOf(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val phone: String = "",
    val distance: Double = 0.0,
    val imgUrl: String = "",
    val address: List<String> = listOf(),
    val name: String = "",
    val price: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0
)

fun List<Businesses>?.mapToRestaurant(): List<Restaurant>{
    return this?.map {
        val categoryList: MutableList<String> = mutableListOf()
        it.categories?.forEach {
            categoryList.add(it.title?:"")
        }
        Restaurant(
            it.id ?: "",
            categoryList,
            it.coordinates?.latitude ?: 0.0,
            it.coordinates?.longitude ?: 0.0,
            it.displayPhone ?: "",
            it.distance?.let {it*0.000621371}?: 0.0,
            it.imageUrl?:"",
            it.location?.displayAddress?: listOf(),
            it.name ?: "",
            it.price ?: "",
            it.rating ?: 0.0,
            it.reviewCount ?: 0
            )
    }?: emptyList()
}
