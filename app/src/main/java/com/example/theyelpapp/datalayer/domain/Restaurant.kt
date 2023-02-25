package com.example.theyelpapp.datalayer.domain

import androidx.room.Entity
import com.example.theyelpapp.datalayer.model.restaurant.Businesses

/**
 * Data class that defines a restaurant
 */

@Entity
data class Restaurant(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val phone: String = "",
    val distance: Double = 0.0,
    val imgUrl: String = "",
    val address: String = "",
    val name: String = "",
    val price: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0
)

fun List<Businesses>?.mapToRestaurant(): List<Restaurant>{

    return this?.map {
        var strAddr = ""
        it.location?.displayAddress?.forEach {
            strAddr = strAddr + it + '\n'
        }
        Restaurant(
            it.id ?: "",
            it.coordinates?.latitude ?: 0.0,
            it.coordinates?.longitude ?: 0.0,
            it.displayPhone ?: "",
            it.distance?.let {it*0.000621371}?: 0.0,
            it.imageUrl?:"",
            strAddr,
            it.name ?: "",
            it.price ?: "",
            it.rating ?: 0.0,
            it.reviewCount ?: 0
            )
    }?: emptyList()
}
