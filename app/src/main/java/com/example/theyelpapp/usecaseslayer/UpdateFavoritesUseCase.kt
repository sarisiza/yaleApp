package com.example.theyelpapp.usecaseslayer

import android.util.Log
import com.example.theyelpapp.datalayer.database.LocalRepository
import com.example.theyelpapp.datalayer.domain.Restaurant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "UpdateFavoritesUseCase"
class UpdateFavoritesUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(restaurant: Restaurant?){
        withContext(ioDispatcher){
            restaurant?.let {
                restaurant.isFavorite = !restaurant.isFavorite
                if (restaurant.isFavorite) {
                    localRepository.insertFavorite(restaurant)
                    Log.d(TAG, "invoke: inserted in database")
                }
                else {
                    localRepository.deleteFavorite(restaurant)
                    Log.d(TAG, "invoke: deleted from database")
                }
            }
        }
    }

}