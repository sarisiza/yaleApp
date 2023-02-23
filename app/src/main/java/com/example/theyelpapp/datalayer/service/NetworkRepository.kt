package com.example.theyelpapp.datalayer.service

import com.example.theyelpapp.datalayer.model.ratings.RatingsResponse
import com.example.theyelpapp.datalayer.model.restaurant.RestaurantsResponse
import com.example.theyelpapp.utils.ApiSortedBy
import retrofit2.Response
import javax.inject.Inject

interface NetworkRepository {

    suspend fun getRestaurants(
        latitude: Double,
        longitude: Double,
        attributes: String,
        sortedBy: ApiSortedBy
    ): Response<RestaurantsResponse>

    suspend fun getRatings(
        id: String,
        sortedBy: ApiSortedBy
    ): Response<RatingsResponse>

}

class NetworkRepositoryImpl @Inject constructor(
    private val yelpApi: YelpApi
    ): NetworkRepository{

    override suspend fun getRestaurants(
        latitude: Double,
        longitude: Double,
        attributes: String,
        sortedBy: ApiSortedBy
    ): Response<RestaurantsResponse> =
        yelpApi.getRestaurants(latitude,longitude, attributes, sortedBy.sorted_by)

    override suspend fun getRatings(
        id: String,
        sortedBy: ApiSortedBy
    ): Response<RatingsResponse> =
        yelpApi.getRatings(id, sortedBy.sorted_by)

}