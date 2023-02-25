package com.example.theyelpapp.utils

sealed class ViewIntents{

    object GET_LOCATION: ViewIntents()

    object RESTAURANT_LIST: ViewIntents()

    object CONFIGURATION_CHANGE: ViewIntents()

    object RESTAURANT_RATINGS: ViewIntents()

    object START_FRAGMENT: ViewIntents()

    object UPDATE_FAVORITE: ViewIntents()

    object GET_FAVORITES: ViewIntents()

}
