package com.example.theyelpapp.usecases

import android.location.Location
import com.example.theyelpapp.data.domain.Restaurant
import com.example.theyelpapp.data.domain.mapToRestaurant
import com.example.theyelpapp.data.service.NetworkRepository
import com.example.theyelpapp.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val networkState: NetworkState
) {

    operator fun invoke(
        location: Location,
        attributes: String,
        sortedBy: ApiSortedBy
    ): Flow<UIState<List<Restaurant>>> = flow {
        emit(UIState.LOADING)
        try {
            if(networkState.isInternetEnabled()){
                val response = networkRepository.getRestaurants(
                    location.latitude,
                    location.longitude,
                    attributes,
                    sortedBy
                )
                if(response.isSuccessful){
                    response.body()?.let {result ->
                        val restaurants = result.businesses.mapToRestaurant()
                        emit(UIState.SUCCESS(restaurants))
                    } ?: throw NoResponseException()
                } else throw NetworkCallFailureException(response.errorBody().toString())
            } else throw InternetConnectionException()
        }catch (e: Exception){
            emit(UIState.ERROR(e))
        }
    }

}