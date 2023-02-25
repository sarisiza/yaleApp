package com.example.theyelpapp.datalayer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.theyelpapp.datalayer.domain.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1
)
abstract class RestaurantDatabase: RoomDatabase() {

    abstract fun getRestaurantsDao(): RestaurantsDAO

}