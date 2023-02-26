package com.example.theyelpapp.usecaseslayer

data class YelpUseCases(
    val getLocation: GetLocationUseCase,
    val getRatings: GetRatingsUseCase,
    val getRestaurants: GetRestaurantsUseCase,
    val updateFavorite: UpdateFavoritesUseCase,
    val getFavoriteRestaurants: GetFavoriteRestaurantsUseCase
)
