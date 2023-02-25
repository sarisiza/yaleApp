package com.example.theyelpapp.datalayer.database

import androidx.room.*
import com.example.theyelpapp.datalayer.domain.Restaurant

@Dao
interface RestaurantsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(vararg restaurant: Restaurant)

    @Delete
    fun deleteFavorite(vararg restaurant: Restaurant)

    @Query("SELECT * FROM Restaurant")
    suspend fun getFavoriteRestaurants(): List<Restaurant>

    @Query("SELECT * FROM Restaurant WHERE id = :id")
    suspend fun getRestaurantById(id: String): Restaurant?

}