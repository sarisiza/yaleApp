package com.example.theyelpapp.usecases

import com.example.theyelpapp.data.domain.Rating
import com.example.theyelpapp.data.domain.mapToRating
import com.example.theyelpapp.data.service.NetworkRepository
import com.example.theyelpapp.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRatingsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val networkState: NetworkState
) {

    operator fun invoke(
        id: String,
        sortedBy: ApiSortedBy
    ): Flow<UIState<List<Rating>>> = flow {
        emit(UIState.LOADING)
        if(networkState.isInternetEnabled()){
            val response = networkRepository.getRatings(
                id,
                sortedBy
            )
            if(response.isSuccessful){
                response.body()?.let {result ->
                    val ratings = result.reviews.mapToRating()
                    emit(UIState.SUCCESS(ratings))
                } ?: throw NoResponseException()
            } else throw NetworkCallFailureException(response.errorBody().toString())
        } else throw InternetConnectionException()
    }.catch {
        emit(UIState.ERROR(it as Exception))
    }

}