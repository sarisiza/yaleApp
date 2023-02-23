package com.example.theyelpapp.utils

sealed class ViewIntents{

    object REQUEST_PERMISSION: ViewIntents()

    object GET_LOCATION: ViewIntents()

    object RESTAURANT_LIST: ViewIntents()

    object RESTAURANT_DETAILS: ViewIntents()

    object RESTAURANT_RATINGS: ViewIntents()

}
