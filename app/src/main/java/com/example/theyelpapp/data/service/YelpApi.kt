package com.example.theyelpapp.data.service

import com.example.theyelpapp.data.model.ratings.RatingsResponse
import com.example.theyelpapp.data.model.restaurant.RestaurantsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpApi {

    @GET(SEARCH_RESTAURANTS)
    suspend fun getRestaurants(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("attributes") attributes: String,
        @Query("sort_by") sorting: String
    ): Response<RestaurantsResponse>

    @GET(PATH_ID + SEARCH_REVIEWS)
    suspend fun getRatings(
        @Path("id") id: String,
        @Query("sort_by") sorting: String
    ): Response<RatingsResponse>

    companion object{
        //https://api.yelp.com/v3/businesses/search?latitude=34.0022861&longitude=-84.6165137&term=restaurants&attributes=hot_and_new&sort_by=best_match&limit=10
        //https://api.yelp.com/v3/businesses/M3UL-rHZ4Sp8k_5bRBPbWg/reviews?limit=20&sort_by=yelp_sort
        const val BASE_URL = "https://api.yelp.com/v3/businesses/"
        private const val SEARCH_RESTAURANTS = "search"
        private const val PATH_ID = "{id}/"
        private const val SEARCH_REVIEWS = "reviews"
    }


}