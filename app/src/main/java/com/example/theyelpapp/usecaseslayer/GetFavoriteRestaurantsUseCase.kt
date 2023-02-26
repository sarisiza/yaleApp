package com.example.theyelpapp.usecaseslayer

import com.example.theyelpapp.datalayer.database.LocalRepository
import com.example.theyelpapp.datalayer.domain.Restaurant
import com.example.theyelpapp.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavoriteRestaurantsUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    operator fun invoke(): Flow<UIState<List<Restaurant>>> = flow{
        emit(localRepository.getFavorites())
    }.flowOn(ioDispatcher)

}