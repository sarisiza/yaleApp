package com.example.theyelpapp.usecaseslayer

import com.example.theyelpapp.datalayer.database.LocalRepository
import com.example.theyelpapp.datalayer.domain.Restaurant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateFavoritesUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    operator fun invoke(restaurant: Restaurant?){
        restaurant?.let {
            restaurant.isFavorite = !restaurant.isFavorite
            if (restaurant.isFavorite)
                localRepository.insertFavorite(restaurant)
            else
                localRepository.deleteFavorite(restaurant)
        } ?: throw Exception("Nothing selected")
    }

}