package com.example.theyelpapp.presentationlayer.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyelpapp.datalayer.domain.Rating
import com.example.theyelpapp.datalayer.domain.Restaurant
import com.example.theyelpapp.usecaseslayer.YelpUseCases
import com.example.theyelpapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "YelpViewModel"

@HiltViewModel
class YelpViewModel @Inject constructor(
    private val yelpUseCases: YelpUseCases
): ViewModel() {

    var locationPermissionEnabled: Boolean = false
    var locationEnabled: Boolean = false

    private val _locationState: MutableLiveData<UIState<Location>> =
        MutableLiveData(UIState.LOADING)
    val locationState: LiveData<UIState<Location>> get() = _locationState

    private val _restaurantsNearMe: MutableLiveData<UIState<List<Restaurant>>> =
        MutableLiveData(UIState.LOADING)
    val restaurantsNearMe: LiveData<UIState<List<Restaurant>>> get() = _restaurantsNearMe

    var selectedRestaurant: Restaurant? = null

    var currentLocation: Location? = null

    private val _restaurantRatings: MutableLiveData<UIState<List<Rating>>> =
        MutableLiveData(UIState.LOADING)
    val restaurantRatings: LiveData<UIState<List<Rating>>> get() = _restaurantRatings

    fun getIntentView(intents: ViewIntents){
        when(intents){
            ViewIntents.RESTAURANT_LIST -> getRestaurantsNearMe()
            ViewIntents.REQUEST_PERMISSION -> {}
            ViewIntents.RESTAURANT_DETAILS -> {}
            ViewIntents.RESTAURANT_RATINGS -> getRestaurantRatings(selectedRestaurant?.id)
            ViewIntents.GET_LOCATION -> getUserLocation()
        }
    }

    private fun getUserLocation(){
        viewModelScope.launch {
            yelpUseCases.getLocation(
                locationPermissionEnabled,
                locationEnabled
            ).collect{
                _locationState.postValue(it)
            }
        }
    }

    private fun getRestaurantsNearMe(){
        try{
            currentLocation?.let {location ->
                viewModelScope.launch {
                    yelpUseCases.getRestaurants(
                        location,
                        "hot_and_new",
                        ApiSortedBy.DISTANCE
                    ).collect {
                        _restaurantsNearMe.postValue(it)
                    }
                }
            } ?: throw LocationFailureException()
        } catch (e: Exception){
            _restaurantsNearMe.postValue(UIState.ERROR(e))
        }
    }

    private fun getRestaurantRatings(id: String?){
        try{
            id?.let {
                viewModelScope.launch {
                    yelpUseCases.getRatings(
                        id,
                        ApiSortedBy.RELEVANT
                    ).collect{
                        _restaurantRatings.postValue(it)
                    }
                }
            }?:throw NoRestaurantException()
        } catch (e: Exception){
            _restaurantRatings.postValue(UIState.ERROR(e))
        }
    }

}