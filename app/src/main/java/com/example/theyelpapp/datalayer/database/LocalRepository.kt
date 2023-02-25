package com.example.theyelpapp.datalayer.database

import android.util.Log
import com.example.theyelpapp.datalayer.domain.Restaurant
import com.example.theyelpapp.utils.UIState
import javax.inject.Inject

private const val TAG = "LocalRepository"

interface LocalRepository {

    fun insertFavorite(restaurant: Restaurant)

    fun deleteFavorite(restaurant: Restaurant)

    suspend fun getFavorites(): UIState<List<Restaurant>>

    suspend fun getFavoritesById(id: String): Restaurant?

}

class LocalRepositoryImpl @Inject constructor(
    private val restaurantsDAO: RestaurantsDAO
): LocalRepository{

    override fun insertFavorite(restaurant: Restaurant) {
        try {
            restaurantsDAO.insertFavorite(restaurant)
        }catch (e: Exception){
            Log.e(TAG, "insertFavorite: ${e.localizedMessage}",e )
        }
    }

    override fun deleteFavorite(restaurant: Restaurant) {
        try{
            restaurantsDAO.deleteFavorite(restaurant)
        }catch (e: Exception){
            Log.e(TAG, "deleteFavorite: ${e.localizedMessage}", e)
        }
    }

    override suspend fun getFavorites(): UIState<List<Restaurant>> {
        return try {
            UIState.SUCCESS(restaurantsDAO.getFavoriteRestaurants())
        } catch (e: Exception){
            UIState.ERROR(e)
        }
    }

    override suspend fun getFavoritesById(id: String): Restaurant? {
        return try {
            restaurantsDAO.getRestaurantById(id)
        } catch (e: Exception){
            Log.e(TAG, "getFavoritesById: ${e.localizedMessage}", e)
            null
        }
    }


}